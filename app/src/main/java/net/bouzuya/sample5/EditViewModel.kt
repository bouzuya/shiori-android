package net.bouzuya.sample5

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditViewModel(val bookmark: Bookmark) : ViewModel() {
    val text = MutableLiveData<String>()
}
