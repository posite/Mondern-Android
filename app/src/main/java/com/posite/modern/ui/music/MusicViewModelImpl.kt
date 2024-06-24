package com.posite.modern.ui.music

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicViewModelImpl @Inject constructor() : ViewModel(), MusicViewModel {
    private val _title = mutableStateOf("")
    override val title: State<String>
        get() = _title

    override fun onDrawerItemClicked(title: String) {
        _title.value = title
    }
}