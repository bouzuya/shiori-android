package net.bouzuya.shiori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.bouzuya.shiori.data.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

class BookmarkEditViewModel(
    private val _bookmarkRepository: BookmarkRepository,
    private val _bookmarkId: Long,
    private val _defaultBookmarkName: String?,
    private val _defaultBookmarkUrl: String?,
    private val _tagRepository: TagRepository
) : ViewModel() {
    private val _cancelEvent = MutableLiveData<Event<Unit>>()
    val cancelEvent: LiveData<Event<Unit>> = _cancelEvent

    private val _okEvent = MutableLiveData<Event<Unit>>()
    val okEvent: LiveData<Event<Unit>> = _okEvent

    val nameText = MutableLiveData<String>()

    val urlText = MutableLiveData<String>()

    val commentText = MutableLiveData<String>()

    private val _allTagList = MutableLiveData<List<Tag>>()
    val allTagList: LiveData<List<Tag>> = _allTagList

    private val _bookmarkTagList = MutableLiveData<List<Tag>>(emptyList())
    val bookmarkTagList: LiveData<List<Tag>> = _bookmarkTagList

    init {
        viewModelScope.launch {
            _allTagList.value = _tagRepository.findAll()
            (if (_bookmarkId == 0L) null else _bookmarkRepository.findById(_bookmarkId))?.let { bookmarkWithTagList ->
                val bookmark = bookmarkWithTagList.bookmark
                nameText.value = bookmark.name
                urlText.value = bookmark.url
                commentText.value = bookmark.comment
                _bookmarkTagList.value = bookmarkWithTagList.tagList
            } ?: {
                nameText.value = _defaultBookmarkName ?: ""
                urlText.value = _defaultBookmarkUrl ?: ""
                commentText.value = ""
            }()
        }
    }

    fun checked(tag: Tag) {
        _bookmarkTagList.value?.also { tagList ->
            _bookmarkTagList.value =
                if (tagList.contains(tag)) tagList.minus(tag)
                else tagList.plus(tag)
        }
    }

    fun cancel() {
        _cancelEvent.value = Event(Unit)
    }

    fun ok() = viewModelScope.launch {
        val name = nameText.value ?: return@launch
        val url = urlText.value ?: return@launch
        val comment = commentText.value ?: return@launch
        val tagList = _bookmarkTagList.value ?: return@launch
        if (_bookmarkId == 0L) {
            val createdAt = Instant.now().atZone(ZoneOffset.UTC).toOffsetDateTime()
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            val bookmark = Bookmark(0, name, url, comment, createdAt)
            _bookmarkRepository.insert(BookmarkWithTagList(bookmark, tagList))
        } else {
            _bookmarkRepository.findById(_bookmarkId)?.also { bookmarkWithTagList ->

                val bookmark = bookmarkWithTagList.bookmark
                _bookmarkRepository.update(
                    BookmarkWithTagList(
                        Bookmark(
                            bookmark.id,
                            name,
                            url,
                            comment,
                            bookmark.createdAt
                        ),
                        tagList
                    )
                )
            }
        }
        _okEvent.value = Event(Unit)
    }
}
