package com.posite.modern.ui.wish

import androidx.compose.runtime.State

interface WishViewModel {
    val wishTitle: State<String>
    val wishDescription: State<String>

    fun wishTitleChanged(title: String)
    fun wishDescriptionChanged(description: String)
}