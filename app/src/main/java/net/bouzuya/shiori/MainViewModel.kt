package net.bouzuya.shiori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.bouzuya.shiori.data.BookmarkRepository
import net.bouzuya.shiori.data.TagRepository

class MainViewModel(
    val bookmarkRepository: BookmarkRepository,
    val tagRepository: TagRepository
) : ViewModel() {
    private val _editResultEvent = MutableLiveData<Event<Boolean>>()
    val editResultEvent: LiveData<Event<Boolean>> = _editResultEvent

    fun editResult(isOk: Boolean) {
        _editResultEvent.value = Event(isOk)
    }
}
