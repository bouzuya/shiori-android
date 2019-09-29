package net.bouzuya.sample5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditViewModel(
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
            _bookmarkRepository.findById(_bookmarkId)?.let { bookmark ->
                nameText.value = bookmark.name
                urlText.value = bookmark.url
            }
        }
    }

    fun cancel() {
        _cancelEvent.value = Event(Unit)
    }

    fun ok() = viewModelScope.launch {
        nameText.value?.also { name ->
            urlText.value?.also { url ->
                _bookmarkRepository.findById(_bookmarkId)?.also { bookmark ->
                    _bookmarkRepository.update(Bookmark(bookmark.id, name, url, bookmark.createdAt))
                }
            }
        }
        _okEvent.value = Event(Unit)
    }
}
