package com.posite.modern.ui.chat

sealed class ChatScreens(val route: String) {
    object LoginScreen : ChatScreens("loginscreen")
    object SignupScreen : ChatScreens("signupscreen")
    object ChatRoomsScreen : ChatScreens("chatroomscreen")
    object ChatScreen : ChatScreens("chatscreen")
}