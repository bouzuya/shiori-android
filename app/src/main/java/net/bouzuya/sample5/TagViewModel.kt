package net.bouzuya.sample5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

class TagViewModel(private val _tagRepository: TagRepository) : ViewModel() {
    private val _editTagEvent = MutableLiveData<Event<Tag>>()
    val editTagEvent: LiveData<Event<Tag>> = _editTagEvent

    private val _nameText = MutableLiveData<String>()
    val nameText: LiveData<String> = _nameText

    private val _tagList = MutableLiveData<List<Tag>>()
    val tagList: LiveData<List<Tag>> = _tagList

    init {
        refreshList()
    }

    fun edit(tag: Tag) {
        _editTagEvent.value = Event(tag)
    }

    fun updateNameText(s: String) {
        _nameText.value = s
    }

    fun insert() = viewModelScope.launch {
        val name = _nameText.value ?: return@launch
        val createdAt = Instant.now().atZone(ZoneOffset.UTC).toOffsetDateTime()
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val tag = Tag(0, name, createdAt)
        _tagRepository.insert(tag)

        _nameText.value = ""
        refreshList()
    }

    private fun refreshList() {
        viewModelScope.launch {
            _tagList.value = _tagRepository.findAll()
        }
    }
}
