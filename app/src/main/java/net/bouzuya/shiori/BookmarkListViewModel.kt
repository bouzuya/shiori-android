package net.bouzuya.shiori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.bouzuya.shiori.data.Bookmark
import net.bouzuya.shiori.data.BookmarkAction
import net.bouzuya.shiori.data.BookmarkRepository

class BookmarkListViewModel(private val _bookmarkRepository: BookmarkRepository) : ViewModel() {

    private val _bookmarkList = MutableLiveData<List<Bookmark>>()
    val bookmarkList: LiveData<List<Bookmark>> = _bookmarkList

    private val _bookmarkActionEvent = MutableLiveData<Event<Pair<BookmarkAction, Bookmark>>>()
    val bookmarkActionEvent: LiveData<Event<Pair<BookmarkAction, Bookmark>>> = _bookmarkActionEvent

    private val _createBookmarkEvent = MutableLiveData<Event<Unit>>()
    val createBookmarkEvent: LiveData<Event<Unit>> = _createBookmarkEvent

    init {
        viewModelScope.launch {
            refreshList()
        }
    }

    fun click(bookmark: Bookmark) {
        // TODO: setting
        handleAction(BookmarkAction.Open, bookmark)
    }

    fun create() {
        _createBookmarkEvent.value = Event(Unit)
    }

    fun longClick(bookmark: Bookmark) {
        // TODO: setting
        handleAction(BookmarkAction.Edit, bookmark)
    }

    fun refresh() = viewModelScope.launch {
        refreshList()
    }

    fun handleAction(action: BookmarkAction, bookmark: Bookmark) {
        _bookmarkActionEvent.value = Event(Pair(action, bookmark))
    }

    private suspend fun refreshList() {
        _bookmarkList.value = _bookmarkRepository.findAll()
    }
}
