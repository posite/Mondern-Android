package com.posite.modern.ui.music

import androidx.compose.runtime.State

interface MusicViewModel {
    val title: State<String>

    fun onDrawerItemClicked(title: String)
}