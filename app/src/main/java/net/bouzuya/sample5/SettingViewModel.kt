package net.bouzuya.sample5

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import net.bouzuya.sample5.data.BookmarkRepository
import net.bouzuya.sample5.data.TagRepository

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
        viewModelScope.launch {
            _bookmarkCount.value = _bookmarkRepository.countAll()
            _tagCount.value = _tagRepository.countAll()
        }
    }
}
