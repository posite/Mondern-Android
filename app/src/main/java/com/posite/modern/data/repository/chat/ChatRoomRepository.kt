package com.posite.modern.data.repository.chat

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.posite.modern.data.remote.model.chat.ChatRoom
import com.posite.modern.util.DataResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRoomRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    suspend fun createRoom(name: String): DataResult<Unit> = try {
        val room = ChatRoom(name = name)
        firestore.collection("rooms").add(room).await()
        DataResult.Success(Unit)
    } catch (e: Exception) {
        DataResult.Error(e)
    }

    suspend fun loadRooms(): DataResult<List<ChatRoom>> = try {
        val querySnapshot = firestore.collection("rooms").get().await()
        val rooms = querySnapshot.documents.map { document ->
            document.toObject(ChatRoom::class.java)!!.copy(id = document.id)
        }
        DataResult.Success(rooms)
    } catch (e: Exception) {
        Log.d("ChatRoomRepository", "loadRooms: Error ${e.message}")
        DataResult.Error(e)
    }

    suspend fun getRoom(roomId: String): DataResult<ChatRoom> = try {
        val documentSnapshot = firestore.collection("rooms").document(roomId).get().await()
        val room = documentSnapshot.toObject(ChatRoom::class.java)!!.copy(id = documentSnapshot.id)
        DataResult.Success(room)
    } catch (e: Exception) {
        DataResult.Error(e)
    }
}