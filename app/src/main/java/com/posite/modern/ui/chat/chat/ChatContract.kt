package com.posite.modern.ui.chat.chat

import com.posite.modern.data.remote.model.chat.ChatMessage
import com.posite.modern.data.remote.model.chat.ChatRoom
import com.posite.modern.data.remote.model.chat.ChatUserInfo
import com.posite.modern.ui.base.UiEffect
import com.posite.modern.ui.base.UiEvent
import com.posite.modern.ui.base.UiState

class ChatContract {
    sealed class ChatEvent : UiEvent {
        data class SendMessage(val message: ChatMessage) : ChatEvent()
        object LoadMessages : ChatEvent()
        data class GetRoom(val roomId: String) : ChatEvent()
        object LoadCurrentUser : ChatEvent()
        object ClearAll : ChatEvent()
        object SetVisible : ChatEvent()
        object SetInvisible : ChatEvent()
    }

    sealed class ChatState {
        object Loading : ChatState()
        object Failed : ChatState()
        data class Success(val roomId: String) : ChatState()
        data class Visible(val visibility: Boolean) : ChatState()
        data class Messages(val messages: List<ChatMessage>) : ChatState()
        data class Room(val room: ChatRoom) : ChatState()
        data class CurrentUser(val currentUser: ChatUserInfo) : ChatState()

    }

    sealed class ChatEffect : UiEffect {

    }

    data class ChatStates(
        val loadState: ChatState,
        val room: ChatState.Room,
        val visible: ChatState.Visible,
        val currentUser: ChatState.CurrentUser,
        val messages: ChatState.Messages
    ) : UiState

}