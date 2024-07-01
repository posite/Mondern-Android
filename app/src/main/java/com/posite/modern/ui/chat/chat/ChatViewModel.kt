package com.posite.modern.ui.chat.chat

import com.posite.modern.data.remote.model.chat.ChatMessage
import com.posite.modern.data.remote.model.chat.ChatUserInfo
import kotlinx.coroutines.flow.StateFlow

interface ChatViewModel {
    val chatMessage: StateFlow<List<ChatMessage>>
    val roomId: StateFlow<String>
    val currentUser: StateFlow<ChatUserInfo>

    fun setRoomId(roomId: String)
    fun sendMessage(message: ChatMessage)
    fun loadCurrentUser()
    fun loadMessages()
}