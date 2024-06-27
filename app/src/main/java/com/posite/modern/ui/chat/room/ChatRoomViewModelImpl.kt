package com.posite.modern.ui.chat.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.remote.model.chat.ChatRoom
import com.posite.modern.data.repository.chat.ChatRoomRepository
import com.posite.modern.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModelImpl @Inject constructor(private val repository: ChatRoomRepository) :
    ChatRoomViewModel, ViewModel() {
    private val _rooms: MutableStateFlow<List<ChatRoom>> = MutableStateFlow(emptyList())
    override val rooms: StateFlow<List<ChatRoom>>
        get() = _rooms

    override fun createRoom(name: String) {
        viewModelScope.launch {
            repository.createRoom(name)
            loadRooms()
        }
    }

    override fun loadRooms() {
        viewModelScope.launch {
            repository.loadRooms().onSuccess {
                _rooms.value = it
            }
        }
    }

}