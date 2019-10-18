package net.bouzuya.shiori

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import net.bouzuya.shiori.data.BookmarkRepository
import net.bouzuya.shiori.data.TagRepository

class SettingViewModel(
    private val _bookmarkRepository: BookmarkRepository,
    private val _tagRepository: TagRepository,
    val appVersion: String,
    databaseVersionNumber: Int
) : ViewModel() {
    private val _bookmarkCount = MutableLiveData<Int>()
    val bookmarkCount: LiveData<String> = Transformations.map(_bookmarkCount) { it.toString() }

    val databaseVersion: String = databaseVersionNumber.toString()

    private val _tagCount = MutableLiveData<Int>()
    val tagCount: LiveData<String> = Transformations.map(_tagCount) { it.toString() }

    init {
        refresh()
    }

    fun deleteAll() = viewModelScope.launch {
        _bookmarkRepository.deleteAll()
        _tagRepository.deleteAll()

        refresh()
    }

    private fun refresh() = viewModelScope.launch {
        _bookmarkCount.value = _bookmarkRepository.countAll()
        _tagCount.value = _tagRepository.countAll()
    }
}
