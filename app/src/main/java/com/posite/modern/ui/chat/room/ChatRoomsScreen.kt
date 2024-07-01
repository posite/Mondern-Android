package com.posite.modern.ui.chat.room

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.posite.modern.R
import com.posite.modern.data.remote.model.chat.ChatRoom

@Composable
fun ChatRoomsScreen(viewModel: ChatRoomViewModel, onJoinClicked: (ChatRoom) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    val rooms = viewModel.rooms.collectAsState()
    viewModel.loadRooms()
    Log.d("rooms", rooms.value.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (showDialog) {
            AlertDialog(onDismissRequest = { showDialog = true },
                title = { Text("Create a new room") },
                text = {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }, confirmButton = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if (name.isNotBlank()) {
                                    showDialog = false
                                    viewModel.createRoom(name)
                                }
                            }
                        ) {
                            Text("Add")
                        }
                        Button(
                            onClick = { showDialog = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                })
        }

        Text("Chat Rooms", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Display a list of chat rooms
        LazyColumn {
            items(rooms.value) { room ->
                RoomItem(room = room) {
                    onJoinClicked(it)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to create a new room
        Button(
            onClick = {
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Room")
        }
    }
}

@Composable
fun RoomItem(room: ChatRoom, onJoinClicked: (ChatRoom) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = room.name, fontSize = 20.sp)
        IconButton(
            onClick = { onJoinClicked(room) },
            modifier = Modifier
                .background(
                    shape = ButtonDefaults.shape,
                    color = colorResource(id = R.color.purple_500)
                )
                .padding(horizontal = 8.dp),
            colors = IconButtonColors(
                containerColor = colorResource(id = R.color.purple_500),
                disabledContentColor = colorResource(id = R.color.white),
                contentColor = colorResource(id = R.color.white),
                disabledContainerColor = colorResource(id = R.color.purple_500)
            )
        ) {
            Text(text = "Join")
        }
    }
}