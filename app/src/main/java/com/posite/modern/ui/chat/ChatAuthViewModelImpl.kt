package com.posite.modern.ui.chat

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.repository.chat.ChatRepository
import com.posite.modern.util.DataResult
import com.posite.modern.util.onError
import com.posite.modern.util.onFail
import com.posite.modern.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatAuthViewModelImpl @Inject constructor(private val chatRepository: ChatRepository) :
    ChatAuthViewModel, ViewModel() {
    private val _authResult = mutableStateOf<DataResult<Boolean>>(DataResult.Loading())
    override val authResult: State<DataResult<Boolean>>
        get() = _authResult

    override fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            chatRepository.signUp(email, password, firstName, lastName)
                .onSuccess {
                    _authResult.value = DataResult.Success(it)
                    Log.d("ChatAuthViewModelImpl", "signUp: Success")
                }
                .onFail {
                    _authResult.value = DataResult.Fail(it, "Failed to sign up")
                    Log.d("ChatAuthViewModelImpl", "signUp: Fail")
                }
                .onError {
                    _authResult.value = DataResult.Error(it)
                    Log.d("ChatAuthViewModelImpl", "signUp: Error ${it.message}")
                }
        }
    }

    override fun login(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = chatRepository.login(email, password)
        }
    }
}