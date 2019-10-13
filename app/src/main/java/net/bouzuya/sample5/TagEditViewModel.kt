package net.bouzuya.sample5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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
            _tagRepository.findById(_tagId)?.let { tag ->
                nameText.value = tag.name
            }
        }
    }

    fun cancel() {
        _cancelEvent.value = Event(Unit)
    }

    fun ok() = viewModelScope.launch {
        nameText.value?.also { name ->
            _tagRepository.findById(_tagId)?.also { tag ->
                _tagRepository.update(Tag(tag.id, name, tag.createdAt))
            }
        }
        _okEvent.value = Event(Unit)
    }
}
