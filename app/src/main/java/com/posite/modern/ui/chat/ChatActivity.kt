package com.posite.modern.ui.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.posite.modern.ui.theme.ModernTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ChatAuthViewModel by viewModels<ChatAuthViewModelImpl>()
            val navHostController = rememberNavController()
            ModernTheme {
                ChatNavigation(viewModel = viewModel, navController = navHostController)
            }
        }
    }
}