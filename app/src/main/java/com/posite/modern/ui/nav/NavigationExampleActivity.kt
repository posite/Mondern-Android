package com.posite.modern.ui.nav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.posite.modern.ui.theme.ModernTheme

class NavigationExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModernTheme {
                NavigationExample()
            }
        }
    }
}

@Composable
fun NavigationExample() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen {
                navController.navigate("SecondScreen/$it")
            }
        }
        composable("SecondScreen/{text}") {
            val text = it.arguments?.getString("text") ?: "none"
            SecondScreen(text) { navController.popBackStack() }
        }

    }
}

@Composable
fun MainScreen(navigateToSecond: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Main Screen",
            style = TextStyle(fontSize = 32.sp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(value = text, onValueChange = { text = it })
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { navigateToSecond(text) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00b0f0))
        ) {
            Text(text = "Go to Next Screen", style = TextStyle(fontSize = 16.sp))
        }

    }
}

@Composable
fun SecondScreen(text: String, navigateToMain: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Second Screen $text",
            style = TextStyle(fontSize = 32.sp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { navigateToMain() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0090ed))
        ) {
            Text(text = "Go to Next Screen", style = TextStyle(fontSize = 16.sp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NavigationExamplePreview() {
    ModernTheme {
        NavigationExample()
    }
}
