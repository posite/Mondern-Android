package com.posite.modern.ui.chat.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.posite.modern.util.onError
import com.posite.modern.util.onException
import com.posite.modern.util.onFail
import com.posite.modern.util.onSuccess

@Composable
fun LoginScreen(
    viewModel: ChatAuthViewModel,
    onNavigationToSignUp: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = 123) {
        viewModel.authResult.collect {
            it.onSuccess {
                onLoginSuccess()
            }.onError {
                Toast.makeText(
                    context,
                    "Failed to login: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }.onFail {
                Toast.makeText(
                    context,
                    "Failed to login: $it",
                    Toast.LENGTH_SHORT
                ).show()
            }.onException {
                Toast.makeText(
                    context,
                    "Failed to login: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                viewModel.login(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Don't have an account? Sign up.",
            modifier = Modifier.clickable { onNavigationToSignUp() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    //LoginScreen()
}