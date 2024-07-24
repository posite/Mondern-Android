package com.posite.modern.ui.chat.auth

import com.posite.modern.ui.base.UiEffect
import com.posite.modern.ui.base.UiEvent
import com.posite.modern.ui.base.UiState

class ChatAuthContract {
    sealed class ChatAuthEvent : UiEvent {
        data class SignUp(
            val email: String,
            val password: String,
            val firstName: String,
            val lastName: String
        ) : ChatAuthEvent()

        data class Login(val email: String, val password: String) : ChatAuthEvent()
    }

    sealed class ChatAuthState {
        object Before : ChatAuthState()
        object Loading : ChatAuthState()
        object Success : ChatAuthState()

    }

    sealed class ChatAuthEffect : UiEffect {
        object ShowError : ChatAuthEffect()
    }

    data class ChatAuthStates(val loadState: ChatAuthState) : UiState
}