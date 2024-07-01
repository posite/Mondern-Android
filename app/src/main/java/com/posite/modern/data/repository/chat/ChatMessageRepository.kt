package com.posite.modern.data.repository.chat

import com.google.firebase.firestore.FirebaseFirestore
import com.posite.modern.data.remote.model.chat.ChatMessage
import com.posite.modern.util.DataResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatMessageRepository @Inject constructor(private val fireStore: FirebaseFirestore) {
    suspend fun sendMessage(roomId: String, message: ChatMessage): DataResult<Unit> = try {
        fireStore.collection("rooms").document(roomId).collection("messages").add(message).await()
        DataResult.Success(Unit)
    } catch (e: Exception) {
        DataResult.Error(e)
    }

    fun getChatMessages(roomId: String): Flow<List<ChatMessage>> = callbackFlow {
        val subscription = fireStore.collection("rooms").document(roomId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { querySnapshot, _ ->
                querySnapshot?.let {
                    trySend(it.documents.map { doc ->
                        doc.toObject(ChatMessage::class.java)!!.copy()
                    }).isSuccess
                }
            }

        awaitClose { subscription.remove() }
    }
}