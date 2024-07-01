package com.posite.modern.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.posite.modern.ui.chat.auth.ChatAuthViewModel
import com.posite.modern.ui.chat.auth.ChatAuthViewModelImpl
import com.posite.modern.ui.chat.chat.ChatViewModel
import com.posite.modern.ui.chat.chat.ChatViewModelImpl
import com.posite.modern.ui.chat.room.ChatRoomViewModel
import com.posite.modern.ui.chat.room.ChatRoomViewModelImpl
import com.posite.modern.ui.theme.ModernTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {
    private var backKeyPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val chatAuthViewModel: ChatAuthViewModel by viewModels<ChatAuthViewModelImpl>()
            val chatRoomViewModel: ChatRoomViewModel by viewModels<ChatRoomViewModelImpl>()
            val chatViewModel: ChatViewModel by viewModels<ChatViewModelImpl>()
            val navHostController = rememberNavController()
            ModernTheme {
                ChatNavigation(
                    chatAuthViewModel = chatAuthViewModel,
                    chatRoomViewModel = chatRoomViewModel,
                    chatViewModel = chatViewModel,
                    navController = navHostController,
                ) {
                    onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
                }
            }
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                    backKeyPressedTime = System.currentTimeMillis()

                    Toast.makeText(this@ChatActivity, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    finish()
                }
            }
        }
}

