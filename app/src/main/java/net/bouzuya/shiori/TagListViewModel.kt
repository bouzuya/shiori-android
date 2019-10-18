package net.bouzuya.shiori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.bouzuya.shiori.data.Tag
import net.bouzuya.shiori.data.TagRepository

class TagListViewModel(private val _tagRepository: TagRepository) : ViewModel() {
    private val _editTagEvent = MutableLiveData<Event<Tag>>()
    val editTagEvent: LiveData<Event<Tag>> = _editTagEvent

    private val _createTagEvent = MutableLiveData<Event<Unit>>()
    val createTagEvent: LiveData<Event<Unit>> = _createTagEvent

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> = _tagList

    init {
        refreshList()
    }

    fun create() {
        _createTagEvent.value = Event(Unit)
    }

    fun edit(tag: Tag) {
        _editTagEvent.value = Event(tag)
    }

    fun refresh() {
        refreshList()
    }

    private fun refreshList() {
        viewModelScope.launch {
            _tagList.value = _tagRepository.findAll()
        }
    }
}
