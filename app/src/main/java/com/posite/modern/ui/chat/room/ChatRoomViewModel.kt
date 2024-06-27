package com.posite.modern.ui.chat.room

import com.posite.modern.data.remote.model.chat.ChatRoom
import kotlinx.coroutines.flow.StateFlow

interface ChatRoomViewModel {
    val rooms: StateFlow<List<ChatRoom>>

    fun createRoom(name: String)
    fun loadRooms()

}