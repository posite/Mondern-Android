package com.posite.modern.ui.chat.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.posite.modern.R
import com.posite.modern.data.remote.model.chat.ChatMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(roomId: String, viewModel: ChatContractViewModel, onBackPressed: () -> Unit) {
    val chatStates = viewModel.currentState
    val lazyColumnState = rememberLazyListState()
    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = chatStates) {
        viewModel.loadCurrentUser()
        viewModel.getRoom(roomId)
        if (chatStates.room.room.id.isNotBlank() && chatStates.currentUser.currentUser.email.isNotBlank()) {
            viewModel.loadMessages()
        }
        scope.launch {
            if (chatStates.loadState is ChatContract.ChatState.Success && chatStates.messages.messages.isNotEmpty()) {
                lazyColumnState.scrollToItem(chatStates.messages.messages.size - 1)
                delay(300)
                viewModel.setVisible()
            }
        }
    }

    if (chatStates.room.room.id == roomId && chatStates.visible.visibility) {
        ChatContent(
            onBackPressed = onBackPressed,
            chatStates = chatStates,
            lazyColumnState = lazyColumnState,
            viewModel = viewModel
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ChatContent(
    onBackPressed: () -> Unit,
    chatStates: ChatContract.ChatStates,
    lazyColumnState: LazyListState,
    viewModel: ChatContractViewModel,
) {
    val text = remember { mutableStateOf("") }

    Scaffold(modifier = Modifier
        .fillMaxSize(), topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

        }, title = { Text(text = chatStates.room.room.name) })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Display the chat messages
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = lazyColumnState
            ) {
                items(chatStates.messages.messages) {
                    ChatMessageItem(message = it, chatStates.currentUser.currentUser.email)
                }
            }

            // Chat input field and send icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                    modifier = Modifier
                        .background(
                            shape = ButtonDefaults.shape,
                            color = colorResource(id = R.color.sky_blue)
                        )
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                )

                IconButton(
                    onClick = {
                        // Send the message when the icon is clicked
                        if (text.value.isNotEmpty()) {
                            viewModel.sendMessage(
                                ChatMessage(
                                    text = text.value,
                                    senderId = chatStates.currentUser.currentUser.email, // Replace with actual sender ID
                                    senderFirstName = chatStates.currentUser.currentUser.firstName, // Replace with actual sender name
                                    timestamp = Instant.now().toEpochMilli()
                                )
                            )
                            text.value = ""
                        }

                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage, currentUserId: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = if (message.senderId == currentUserId) Alignment.End else Alignment.Start
    ) {
        Text(
            text = message.senderFirstName,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .background(
                    if (message.senderId == currentUserId) colorResource(id = R.color.sky_blue) else Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = message.text,
                color = Color.White,
                style = TextStyle(fontSize = 16.sp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = formatTimestamp(message.timestamp), // Replace with actual timestamp logic
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Gray
            )
        )
    }
}


private fun formatTimestamp(timestamp: Long): String {
    val messageDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
    val now = LocalDateTime.now()

    return when {
        isSameDay(messageDateTime, now) -> "today ${formatTime(messageDateTime)}"
        isSameDay(messageDateTime.plusDays(1), now) -> "yesterday ${formatTime(messageDateTime)}"
        else -> formatDate(messageDateTime)
    }
}


private fun isSameDay(dateTime1: LocalDateTime, dateTime2: LocalDateTime): Boolean {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return dateTime1.format(formatter) == dateTime2.format(formatter)
}


private fun formatTime(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return formatter.format(dateTime)
}

private fun formatDate(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
    return formatter.format(dateTime)
}