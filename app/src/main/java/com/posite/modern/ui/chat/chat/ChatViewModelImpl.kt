package com.posite.modern.ui.chat.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.remote.model.chat.ChatMessage
import com.posite.modern.data.remote.model.chat.ChatUserInfo
import com.posite.modern.data.repository.chat.ChatMessageRepository
import com.posite.modern.data.repository.chat.ChatRepository
import com.posite.modern.util.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModelImpl @Inject constructor(
    private val chatMessageRepository: ChatMessageRepository,
    private val userRepository: ChatRepository
) :
    ChatViewModel, ViewModel() {
    private val _chatMessage = MutableStateFlow<List<ChatMessage>>(emptyList())
    override val chatMessage: StateFlow<List<ChatMessage>>
        get() = _chatMessage

    private val _roomId = MutableStateFlow("")
    override val roomId: StateFlow<String>
        get() = _roomId

    private val _currentUser = MutableStateFlow<ChatUserInfo>(ChatUserInfo())
    override val currentUser: StateFlow<ChatUserInfo>
        get() = _currentUser


    override fun setRoomId(roomId: String) {
        this._roomId.value = roomId
    }

    override fun sendMessage(message: ChatMessage) {
        viewModelScope.launch {
            chatMessageRepository.sendMessage(_roomId.value, message)
            loadMessages()
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
            if (_roomId.value.isBlank().not()) {
                chatMessageRepository.getChatMessages(_roomId.value)
                    .collect {
                        _chatMessage.value = it
                        Log.d("ChatViewModelImpl", "loadMessages: ${_chatMessage.value}")
                    }
            }
        }
    }
}