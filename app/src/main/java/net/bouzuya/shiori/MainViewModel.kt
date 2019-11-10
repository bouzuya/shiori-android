package net.bouzuya.shiori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.bouzuya.shiori.data.BookmarkRepository
import net.bouzuya.shiori.data.PreferenceRepository
import net.bouzuya.shiori.data.TagRepository

class MainViewModel(
    val bookmarkRepository: BookmarkRepository,
    val preferenceRepository: PreferenceRepository,
    val tagRepository: TagRepository
) : ViewModel() {
    private val _editResultEvent = MutableLiveData<Event<Boolean>>()
    val editResultEvent: LiveData<Event<Boolean>> = _editResultEvent

    private val _searchQuery = MutableLiveData<String?>()
    val searchQuery: LiveData<String?> = _searchQuery

    val isVisibleSearchIcon: Boolean
        get() = _searchQuery.value != null

    fun editResult(isOk: Boolean) {
        _editResultEvent.value = Event(isOk)
    }

    fun hideSearchIcon() {
        _searchQuery.value = null
    }

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun showSearchIcon() {
        _searchQuery.value = _searchQuery.value ?: ""
    }
}
