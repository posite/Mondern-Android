package com.posite.modern.ui.chat.chat

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.remote.model.chat.ChatMessage
import com.posite.modern.data.remote.model.chat.ChatRoom
import com.posite.modern.data.remote.model.chat.ChatUserInfo
import com.posite.modern.data.repository.chat.ChatMessageRepository
import com.posite.modern.data.repository.chat.ChatRepository
import com.posite.modern.data.repository.chat.ChatRoomRepository
import com.posite.modern.ui.base.BaseViewModel
import com.posite.modern.util.DataResult
import com.posite.modern.util.onError
import com.posite.modern.util.onException
import com.posite.modern.util.onFail
import com.posite.modern.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatContractViewModel @Inject constructor(
    private val chatMessageRepository: ChatMessageRepository,
    private val userRepository: ChatRepository,
    private val chatRoomRepository: ChatRoomRepository
) : BaseViewModel<ChatContract.ChatEvent, ChatContract.ChatStates, ChatContract.ChatEffect>() {
    override fun createInitialState(): ChatContract.ChatStates {
        return ChatContract.ChatStates(
            ChatContract.ChatState.Loading,
            ChatContract.ChatState.Room(ChatRoom()),
            ChatContract.ChatState.Visible(false),
            ChatContract.ChatState.CurrentUser(ChatUserInfo()),
            ChatContract.ChatState.Messages(emptyList())
        )
    }

    override fun handleEvent(event: ChatContract.ChatEvent) {
        viewModelScope.launch {
            when (event) {
                is ChatContract.ChatEvent.SendMessage -> {
                    chatMessageRepository.sendMessage(currentState.room.room.id, event.message)
                        .onSuccess {

                        }.onError {

                        }.onException {

                        }.onFail {

                        }
                }

                is ChatContract.ChatEvent.LoadMessages -> {
                    chatMessageRepository.getChatMessages(currentState.room.room.id).collect {
                        setState {
                            copy(
                                loadState = ChatContract.ChatState.Success,
                                messages = ChatContract.ChatState.Messages(it),
                                visible = ChatContract.ChatState.Visible(true)
                            )
                        }
                    }

                }

                is ChatContract.ChatEvent.GetRoom -> {
                    chatRoomRepository.getRoom(event.roomId).onSuccess {
                        setState { copy(room = ChatContract.ChatState.Room(it)) }
                    }
                }

                is ChatContract.ChatEvent.LoadCurrentUser -> {
                    when (val result = userRepository.getCurrentUser()) {
                        is DataResult.Success -> {
                            setState { copy(currentUser = ChatContract.ChatState.CurrentUser(result.data)) }
                        }

                        is DataResult.Error -> {}
                        is DataResult.Fail -> {}
                        is DataResult.Loading -> {}

                    }
                }

                is ChatContract.ChatEvent.ClearAll -> {
                    setState {
                        copy(
                            loadState = ChatContract.ChatState.Loading,
                            visible = ChatContract.ChatState.Visible(false)
                        )
                    }
                }

                is ChatContract.ChatEvent.SetVisible -> {
                    Log.d("ChatContractViewModel", "SetVisible")
                    setState { copy(visible = ChatContract.ChatState.Visible(true)) }
                }

                is ChatContract.ChatEvent.SetInvisible -> {
                    Log.d("ChatContractViewModel", "SetInvisible")
                    setState { copy(visible = ChatContract.ChatState.Visible(false)) }
                }
            }
        }

    }

    fun getRoom(roomId: String) {
        setEvent(ChatContract.ChatEvent.GetRoom(roomId))
    }

    fun loadCurrentUser() {
        setEvent(ChatContract.ChatEvent.LoadCurrentUser)
    }

    fun sendMessage(message: ChatMessage) {
        setEvent(ChatContract.ChatEvent.SendMessage(message))
    }

    fun loadMessages() {
        setEvent(ChatContract.ChatEvent.LoadMessages)
    }

    fun clearAll() {
        setEvent(ChatContract.ChatEvent.ClearAll)
    }

    fun setVisible() {
        setEvent(ChatContract.ChatEvent.SetVisible)
    }

    fun setInvisible() {
        setEvent(ChatContract.ChatEvent.SetInvisible)
    }
}