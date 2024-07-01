package com.posite.modern.ui.chat.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.repository.chat.ChatRepository
import com.posite.modern.util.DataResult
import com.posite.modern.util.onError
import com.posite.modern.util.onFail
import com.posite.modern.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatAuthViewModelImpl @Inject constructor(private val chatRepository: ChatRepository) :
    ChatAuthViewModel, ViewModel() {
    private val _authResult = MutableSharedFlow<DataResult<Boolean>>()
    override val authResult: SharedFlow<DataResult<Boolean>>
        get() = _authResult


    override fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            chatRepository.signUp(email, password, firstName, lastName)
                .onSuccess {
                    login(email, password)
                    Log.d("ChatAuthViewModelImpl", "signUp: Success")
                }
                .onFail {
                    _authResult.emit(DataResult.Fail(it, "Failed to sign up"))
                    Log.d("ChatAuthViewModelImpl", "signUp: Fail")
                }
                .onError {
                    _authResult.emit(DataResult.Error(it))
                    Log.d("ChatAuthViewModelImpl", "signUp: Error ${it.message}")
                }
        }
    }

    override fun login(email: String, password: String) {
        viewModelScope.launch {
            chatRepository.login(email, password)
                .onSuccess {
                    _authResult.emit(DataResult.Success(true))
                    Log.d("ChatAuthViewModelImpl", "login: Success")
                }
                .onFail {
                    _authResult.emit(DataResult.Fail(it, "Failed to login"))
                    Log.d("ChatAuthViewModelImpl", "login: Fail")
                }
                .onError {
                    _authResult.emit(DataResult.Error(it))
                    Log.d("ChatAuthViewModelImpl", "login: Error ${it.message}")
                }
        }
    }
}