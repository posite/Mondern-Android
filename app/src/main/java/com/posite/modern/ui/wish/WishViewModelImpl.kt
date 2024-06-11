package com.posite.modern.ui.wish

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class WishViewModelImpl : ViewModel(), WishViewModel {
    private val _wishTitle = mutableStateOf("")
    override val wishTitle: State<String>
        get() = _wishTitle

    private val _wishDescription = mutableStateOf("")
    override val wishDescription: State<String>
        get() = _wishDescription

    override fun wishTitleChanged(title: String) {
        _wishTitle.value = title
    }

    override fun wishDescriptionChanged(description: String) {
        _wishDescription.value = description
    }
}