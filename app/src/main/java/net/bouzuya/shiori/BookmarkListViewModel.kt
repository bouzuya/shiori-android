package net.bouzuya.shiori

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import net.bouzuya.shiori.data.Bookmark
import net.bouzuya.shiori.data.BookmarkAction
import net.bouzuya.shiori.data.BookmarkRepository
import net.bouzuya.shiori.data.BookmarkWithTagList

class BookmarkListViewModel(private val _bookmarkRepository: BookmarkRepository) : ViewModel() {

    private val _bookmarkWithTagListList = MutableLiveData<List<BookmarkWithTagList>>()
    val bookmarkList: LiveData<List<Bookmark>> =
        Transformations.map(_bookmarkWithTagListList) { bookmarkWithTagListList -> bookmarkWithTagListList.map { it.bookmark } }

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
        _bookmarkWithTagListList.value = _bookmarkRepository.findAll()
    }
}
