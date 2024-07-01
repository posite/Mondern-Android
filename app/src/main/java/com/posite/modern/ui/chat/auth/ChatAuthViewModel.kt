package com.posite.modern.ui.chat.auth

import com.posite.modern.util.DataResult
import kotlinx.coroutines.flow.SharedFlow

interface ChatAuthViewModel {
    val authResult: SharedFlow<DataResult<Boolean>>

    fun signUp(email: String, password: String, firstName: String, lastName: String)
    fun login(email: String, password: String)
}