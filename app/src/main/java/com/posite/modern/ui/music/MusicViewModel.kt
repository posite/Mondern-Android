package com.posite.modern.ui.music

import androidx.compose.runtime.State

interface MusicViewModel {
    val currentDrawerScreen: State<MusicScreen.MusicDrawerScreens>
    val currentBottomScreen: State<MusicScreen.MusicBottomScreen>

    fun onDrawerItemClicked(screen: MusicScreen.MusicDrawerScreens)
    fun onBottomBarClicked(screen: MusicScreen.MusicBottomScreen)
}