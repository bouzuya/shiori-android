package net.bouzuya.sample5

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import net.bouzuya.sample5.data.BookmarkRepository

class SettingViewModel(
    private val _bookmarkRepository: BookmarkRepository,
    val appVersion: String,
    databaseVersionNumber: Int
) : ViewModel() {
    private val _bookmarkCount = MutableLiveData<Int>()
    val bookmarkCount: LiveData<String> = Transformations.map(_bookmarkCount) { it.toString() }

    val databaseVersion: String = databaseVersionNumber.toString()

    init {
        viewModelScope.launch {
            _bookmarkCount.value = _bookmarkRepository.findAll().size
        }
    }
}
