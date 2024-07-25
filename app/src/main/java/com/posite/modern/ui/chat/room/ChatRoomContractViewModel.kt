package com.posite.modern.ui.chat.room

import androidx.lifecycle.viewModelScope
import com.posite.modern.data.repository.chat.ChatRoomRepository
import com.posite.modern.ui.base.BaseViewModel
import com.posite.modern.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomContractViewModel @Inject constructor(private val repository: ChatRoomRepository) :
    BaseViewModel<ChatRoomContract.ChatRoomEvent, ChatRoomContract.ChatRoomStates, ChatRoomContract.ChatRoomEffect>() {
    override fun createInitialState(): ChatRoomContract.ChatRoomStates {
        return ChatRoomContract.ChatRoomStates(
            ChatRoomContract.ChatRoomState.Loading,
            ChatRoomContract.ChatRoomState.Rooms(emptyList())
        )
    }

    override fun handleEvent(event: ChatRoomContract.ChatRoomEvent) {
        viewModelScope.launch {
            when (event) {
                is ChatRoomContract.ChatRoomEvent.LoadRooms -> {
                    repository.loadRooms().onSuccess {
                        setState {
                            copy(
                                loadState = ChatRoomContract.ChatRoomState.Success,
                                rooms = ChatRoomContract.ChatRoomState.Rooms(it)
                            )
                        }
                    }
                }

                is ChatRoomContract.ChatRoomEvent.CreateRoom -> {
                    repository.createRoom(event.name).onSuccess {
                        loadRooms()
                    }
                }
            }
        }
    }

    fun loadRooms() {
        setEvent(ChatRoomContract.ChatRoomEvent.LoadRooms)
    }

    fun createRoom(name: String) {
        setEvent(ChatRoomContract.ChatRoomEvent.CreateRoom(name))
    }
}