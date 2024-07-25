package com.posite.modern.ui.chat.room

import com.posite.modern.data.remote.model.chat.ChatRoom
import com.posite.modern.ui.base.UiEffect
import com.posite.modern.ui.base.UiEvent
import com.posite.modern.ui.base.UiState

class ChatRoomContract {
    sealed class ChatRoomEvent : UiEvent {
        object LoadRooms : ChatRoomEvent()
        data class CreateRoom(val name: String) : ChatRoomEvent()
    }

    sealed class ChatRoomState {
        object Loading : ChatRoomState()
        object Success : ChatRoomState()
        data class Rooms(val rooms: List<ChatRoom>) : ChatRoomState()
    }

    sealed class ChatRoomEffect : UiEffect {

    }

    data class ChatRoomStates(val loadState: ChatRoomState, val rooms: ChatRoomState.Rooms) :
        UiState
}