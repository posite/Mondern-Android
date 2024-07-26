package com.posite.modern.ui.chat.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.remote.model.chat.ChatMessage
import com.posite.modern.data.remote.model.chat.ChatRoom
import com.posite.modern.data.remote.model.chat.ChatUserInfo
import com.posite.modern.data.repository.chat.ChatMessageRepository
import com.posite.modern.data.repository.chat.ChatRepository
import com.posite.modern.data.repository.chat.ChatRoomRepository
import com.posite.modern.util.DataResult
import com.posite.modern.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModelImpl @Inject constructor(
    private val chatMessageRepository: ChatMessageRepository,
    private val userRepository: ChatRepository,
    private val chatRoomRepository: ChatRoomRepository
) :
    ChatViewModel, ViewModel() {
    private val _chatMessage = MutableStateFlow<List<ChatMessage>>(emptyList())
    override val chatMessage: StateFlow<List<ChatMessage>>
        get() = _chatMessage

    private val _room = MutableStateFlow(ChatRoom())
    override val room: StateFlow<ChatRoom>
        get() = _room

    private val _currentUser = MutableStateFlow<ChatUserInfo>(ChatUserInfo())
    override val currentUser: StateFlow<ChatUserInfo>
        get() = _currentUser

    override fun getRoom(roomId: String) {
        viewModelScope.launch {
            chatRoomRepository.getRoom(roomId).onSuccess {
                _room.value = it
            }
        }
    }


    override fun sendMessage(message: ChatMessage) {
        viewModelScope.launch {
            chatMessageRepository.sendMessage(_room.value.id, message)
        }
    }

    override fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is DataResult.Success -> _currentUser.value = result.data
                is DataResult.Error -> {}
                is DataResult.Fail -> {}
                is DataResult.Loading -> {}
            }
        }
    }

    override fun loadMessages() {
        viewModelScope.launch {
            chatMessageRepository.getChatMessages(_room.value.id)
                .collect {
                    _chatMessage.value = it
                    Log.d("ChatViewModelImpl", "loadMessages: ${_chatMessage.value}")
                }
        }
    }
}