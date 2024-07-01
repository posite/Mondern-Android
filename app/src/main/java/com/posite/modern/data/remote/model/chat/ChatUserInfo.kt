package com.posite.modern.data.remote.model.chat

data class ChatUserInfo(
    val firstName: String,
    val lastName: String,
    val email: String,
) {
    constructor() : this("", "", "")
}