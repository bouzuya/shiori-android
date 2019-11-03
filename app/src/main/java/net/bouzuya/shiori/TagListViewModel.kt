package net.bouzuya.shiori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.bouzuya.shiori.data.Tag
import net.bouzuya.shiori.data.TagRepository

class TagListViewModel(private val _tagRepository: TagRepository) : ViewModel() {
    private val _createTagEvent = MutableLiveData<Event<Unit>>()
    val createTagEvent: LiveData<Event<Unit>> = _createTagEvent

    private val _tagActionEvent = MutableLiveData<Event<Pair<TagAction, Tag>>>()
    val tagActionEvent: LiveData<Event<Pair<TagAction, Tag>>> = _tagActionEvent

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> = _tagList

    init {
        refreshList()
    }

    fun create() {
        _createTagEvent.value = Event(Unit)
    }

    fun delete(tag: Tag) = viewModelScope.launch {
        _tagRepository.deleteById(tag.id)

        refresh()
    }

    fun edit(tag: Tag) {
        handleAction(TagAction.Edit, tag)
    }

    fun handleAction(action: TagAction, tag: Tag) {
        _tagActionEvent.value = Event(Pair(action, tag))
    }

    fun open(tag: Tag) {
        handleAction(TagAction.Open, tag)
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
