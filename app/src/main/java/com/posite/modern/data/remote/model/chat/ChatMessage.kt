package com.posite.modern.data.remote.model.chat

data class ChatMessage(
    val senderFirstName: String = "",
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

