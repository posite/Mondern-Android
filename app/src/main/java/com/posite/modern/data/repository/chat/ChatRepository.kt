package com.posite.modern.data.repository.chat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.posite.modern.data.remote.model.chat.UserInfo
import com.posite.modern.util.DataResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): DataResult<Boolean> = try {
        auth.createUserWithEmailAndPassword(email, password).await()
        saveUserToFirestore(UserInfo(firstName, lastName, email))
        DataResult.Success(true)
    } catch (e: Exception) {
        DataResult.Error(e)
    }

    suspend fun login(email: String, password: String): DataResult<Boolean> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            DataResult.Success(true)
        } catch (e: Exception) {
            DataResult.Error(e)
        }

    private suspend fun saveUserToFirestore(user: UserInfo) {
        firestore.collection("users").document(user.email).set(user).await()
    }

}