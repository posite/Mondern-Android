package com.posite.modern.ui.chat.auth

import androidx.lifecycle.viewModelScope
import com.posite.modern.data.repository.chat.ChatRepository
import com.posite.modern.ui.base.BaseViewModel
import com.posite.modern.util.onFail
import com.posite.modern.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatAuthContractViewModel @Inject constructor(private val chatRepository: ChatRepository) :
    BaseViewModel<ChatAuthContract.ChatAuthEvent, ChatAuthContract.ChatAuthStates, ChatAuthContract.ChatAuthEffect>() {
    override fun createInitialState(): ChatAuthContract.ChatAuthStates {
        return ChatAuthContract.ChatAuthStates(ChatAuthContract.ChatAuthState.Before)
    }

    override fun handleEvent(event: ChatAuthContract.ChatAuthEvent) {
        viewModelScope.launch {
            when (event) {
                is ChatAuthContract.ChatAuthEvent.SignUp -> {
                    setState { ChatAuthContract.ChatAuthStates(ChatAuthContract.ChatAuthState.Loading) }
                    chatRepository.signUp(
                        event.email,
                        event.password,
                        event.firstName,
                        event.lastName
                    ).onSuccess {
                        login(event.email, event.password)
                    }.onFail {
                        setEffect { ChatAuthContract.ChatAuthEffect.ShowError }
                    }
                }

                is ChatAuthContract.ChatAuthEvent.Login -> {
                    chatRepository.login(event.email, event.password).onSuccess {
                        setState { ChatAuthContract.ChatAuthStates(ChatAuthContract.ChatAuthState.Success) }
                    }.onFail {
                        setEffect { ChatAuthContract.ChatAuthEffect.ShowError }
                    }
                }
            }
        }
    }

    fun singUp(email: String, password: String, firstName: String, lastName: String) {
        setEvent(ChatAuthContract.ChatAuthEvent.SignUp(email, password, firstName, lastName))
    }

    fun login(email: String, password: String) {
        setEvent(ChatAuthContract.ChatAuthEvent.Login(email, password))
    }
}