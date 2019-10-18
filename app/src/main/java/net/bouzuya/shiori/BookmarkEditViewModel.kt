package net.bouzuya.shiori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.bouzuya.shiori.data.Bookmark
import net.bouzuya.shiori.data.BookmarkRepository
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

class BookmarkEditViewModel(
    private val _bookmarkRepository: BookmarkRepository,
    private val _bookmarkId: Long
) : ViewModel() {
    private val _cancelEvent = MutableLiveData<Event<Unit>>()
    val cancelEvent: LiveData<Event<Unit>> = _cancelEvent

    private val _okEvent = MutableLiveData<Event<Unit>>()
    val okEvent: LiveData<Event<Unit>> = _okEvent

    val nameText = MutableLiveData<String>()

    val urlText = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            (if (_bookmarkId == 0L) null else _bookmarkRepository.findById(_bookmarkId))?.let { bookmark ->
                nameText.value = bookmark.name
                urlText.value = bookmark.url
            } ?: {
                nameText.value = ""
                urlText.value = ""
            }()
        }
    }

    fun cancel() {
        _cancelEvent.value = Event(Unit)
    }

    fun ok() = viewModelScope.launch {
        nameText.value?.also { name ->
            urlText.value?.also { url ->
                if (_bookmarkId == 0L) {
                    val createdAt = Instant.now().atZone(ZoneOffset.UTC).toOffsetDateTime()
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                    val bookmark = Bookmark(0, name, url, createdAt)
                    _bookmarkRepository.insert(bookmark)
                } else {
                    _bookmarkRepository.findById(_bookmarkId)?.also { bookmark ->
                        _bookmarkRepository.update(
                            Bookmark(
                                bookmark.id,
                                name,
                                url,
                                bookmark.createdAt
                            )
                        )
                    }
                }
            }
        }
        _okEvent.value = Event(Unit)
    }
}
