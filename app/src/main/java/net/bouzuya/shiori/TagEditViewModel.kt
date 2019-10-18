package net.bouzuya.shiori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.bouzuya.shiori.data.Tag
import net.bouzuya.shiori.data.TagRepository
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

class TagEditViewModel(
    private val _tagRepository: TagRepository,
    private val _tagId: Long
) : ViewModel() {
    private val _cancelEvent = MutableLiveData<Event<Unit>>()
    val cancelEvent: LiveData<Event<Unit>> = _cancelEvent

    private val _okEvent = MutableLiveData<Event<Unit>>()
    val okEvent: LiveData<Event<Unit>> = _okEvent

    val nameText = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            (if (_tagId == 0L) null else _tagRepository.findById(_tagId))?.let { tag ->
                nameText.value = tag.name
            } ?: {
                nameText.value = ""
            }()
        }
    }

    fun cancel() {
        _cancelEvent.value = Event(Unit)
    }

    fun ok() = viewModelScope.launch {
        nameText.value?.also { name ->
            if (_tagId == 0L) {
                val createdAt = Instant.now().atZone(ZoneOffset.UTC).toOffsetDateTime()
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                val tag = Tag(0L, name, createdAt)
                _tagRepository.insert(tag)
            } else {
                _tagRepository.findById(_tagId)?.also { tag ->
                    _tagRepository.update(Tag(tag.id, name, tag.createdAt))
                }
            }
        }
        _okEvent.value = Event(Unit)
    }
}
