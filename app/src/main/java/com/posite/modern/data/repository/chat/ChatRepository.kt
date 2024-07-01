package com.posite.modern.data.repository.chat

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.posite.modern.data.remote.model.chat.ChatUserInfo
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
        saveUserToFirestore(ChatUserInfo(firstName, lastName, email))
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

    suspend fun getCurrentUser(): DataResult<ChatUserInfo> = try {
        val uid = auth.currentUser?.email
        if (uid != null) {
            val userDocument = firestore.collection("users").document(uid).get().await()
            val user = userDocument.toObject(ChatUserInfo::class.java)
            if (user != null) {
                Log.d("user2", "$uid")
                DataResult.Success(user)
            } else {
                DataResult.Error(Exception("User data not found"))
            }
        } else {
            DataResult.Error(Exception("User not authenticated"))
        }
    } catch (e: Exception) {
        DataResult.Error(e)
    }

    private suspend fun saveUserToFirestore(user: ChatUserInfo) {
        firestore.collection("users").document(user.email).set(user).await()
    }

}