package com.posite.modern.ui.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.posite.modern.ui.chat.auth.ChatAuthViewModel
import com.posite.modern.ui.chat.auth.ChatAuthViewModelImpl
import com.posite.modern.ui.chat.room.ChatRoomViewModel
import com.posite.modern.ui.chat.room.ChatRoomViewModelImpl
import com.posite.modern.ui.theme.ModernTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val chatAuthViewModel: ChatAuthViewModel by viewModels<ChatAuthViewModelImpl>()
            val chatRoomViewModel: ChatRoomViewModel by viewModels<ChatRoomViewModelImpl>()
            val navHostController = rememberNavController()
            ModernTheme {
                ChatNavigation(
                    chatAuthViewModel = chatAuthViewModel,
                    chatRoomViewModel = chatRoomViewModel,
                    navController = navHostController
                )
            }
        }
    }
}