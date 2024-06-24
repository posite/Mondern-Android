package com.posite.modern.ui.music

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.compose.rememberNavController
import com.posite.modern.ui.theme.ModernTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModernTheme {
                val viewModel: MusicViewModel by viewModels<MusicViewModelImpl>()
                MusicNavigation(
                    viewModel,
                    rememberNavController(),
                    rememberDrawerState(initialValue = DrawerValue.Closed)
                )
            }
        }
    }
}