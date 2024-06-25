package com.posite.modern.ui.music

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicViewModelImpl @Inject constructor() : ViewModel(), MusicViewModel {
    private val _currentDrawerScreen =
        mutableStateOf<MusicScreen.MusicDrawerScreens>(MusicScreen.MusicDrawerScreens.Home)
    override val currentDrawerScreen: State<MusicScreen.MusicDrawerScreens>
        get() = _currentDrawerScreen

    private val _currentBottomScreen =
        mutableStateOf<MusicScreen.MusicBottomScreen>(MusicScreen.MusicBottomScreen.Home)
    override val currentBottomScreen: State<MusicScreen.MusicBottomScreen>
        get() = _currentBottomScreen

    override fun onDrawerItemClicked(screen: MusicScreen.MusicDrawerScreens) {
        _currentDrawerScreen.value = screen
    }

    override fun onBottomBarClicked(screen: MusicScreen.MusicBottomScreen) {
        _currentBottomScreen.value = screen
    }
}