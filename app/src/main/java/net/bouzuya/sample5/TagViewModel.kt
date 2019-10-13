package net.bouzuya.sample5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TagViewModel(private val _tagRepository: TagRepository) : ViewModel() {
    private val _editTagEvent = MutableLiveData<Event<Tag>>()
    val editTagEvent: LiveData<Event<Tag>> = _editTagEvent

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> = _tagList

    init {
        viewModelScope.launch {
            _tagList.value = _tagRepository.findAll()
        }
    }

    fun edit(tag: Tag) {
        _editTagEvent.value = Event(tag)
    }
}
