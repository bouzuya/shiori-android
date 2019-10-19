package net.bouzuya.shiori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.bouzuya.shiori.data.Bookmark
import net.bouzuya.shiori.data.BookmarkRepository

class BookmarkListViewModel(private val _bookmarkRepository: BookmarkRepository) : ViewModel() {

    private val _bookmarkList = MutableLiveData<List<Bookmark>>()
    val bookmarkList: LiveData<List<Bookmark>> = _bookmarkList

    private val _createBookmarkEvent = MutableLiveData<Event<Unit>>()
    val createBookmarkEvent: LiveData<Event<Unit>> = _createBookmarkEvent

    private val _editBookmarkEvent = MutableLiveData<Event<Bookmark>>()
    val editBookmarkEvent: LiveData<Event<Bookmark>> = _editBookmarkEvent

    private val _openBookmarkEvent = MutableLiveData<Event<Bookmark>>()
    val openBookmarkEvent: LiveData<Event<Bookmark>> = _openBookmarkEvent

    init {
        viewModelScope.launch {
            refreshList()
        }
    }

    fun click(bookmark: Bookmark) {
        // TODO: setting
        open(bookmark)
    }

    fun create() {
        _createBookmarkEvent.value = Event(Unit)
    }

    fun edit(bookmark: Bookmark) {
        _editBookmarkEvent.value = Event(bookmark)
    }

    fun longClick(bookmark: Bookmark) {
        // TODO: setting
        edit(bookmark)
    }

    fun open(bookmark: Bookmark) {
        _openBookmarkEvent.value = Event(bookmark)
    }

    fun refresh() = viewModelScope.launch {
        refreshList()
    }

    private suspend fun refreshList() {
        _bookmarkList.value = _bookmarkRepository.findAll()
    }
}
