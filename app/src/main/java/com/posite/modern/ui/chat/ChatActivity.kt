package com.posite.modern.ui.chat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.posite.modern.ui.chat.auth.ChatAuthContractViewModel
import com.posite.modern.ui.chat.chat.ChatViewModel
import com.posite.modern.ui.chat.chat.ChatViewModelImpl
import com.posite.modern.ui.chat.room.ChatRoomViewModel
import com.posite.modern.ui.chat.room.ChatRoomViewModelImpl
import com.posite.modern.ui.theme.ModernTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {
    private var backKeyPressedTime = 0L
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val chatAuthViewModel: ChatAuthContractViewModel by viewModels<ChatAuthContractViewModel>()
            val chatRoomViewModel: ChatRoomViewModel by viewModels<ChatRoomViewModelImpl>()
            val chatViewModel: ChatViewModel by viewModels<ChatViewModelImpl>()
            navHostController = rememberNavController()
            ModernTheme {
                ChatNavigation(
                    chatAuthContractViewModel = chatAuthViewModel,
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
                if (navHostController.currentDestination?.route == ChatScreens.ChatRoomsScreen.route) {
                    if (backKeyPressedTime + 2000 > System.currentTimeMillis()) {
                        finish()
                    } else {
                        Toast.makeText(
                            this@ChatActivity,
                            "Press back again to exit",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    backKeyPressedTime = System.currentTimeMillis()
                } else {
                    navHostController.navigateUp()
                }
            }
        }
}

