package com.posite.modern.ui.chat

import androidx.compose.runtime.State
import com.posite.modern.util.DataResult

interface ChatAuthViewModel {
    val authResult: State<DataResult<Boolean>>

    fun signUp(email: String, password: String, firstName: String, lastName: String)
    fun login(email: String, password: String)
}