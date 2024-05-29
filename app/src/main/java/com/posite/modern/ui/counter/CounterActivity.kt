package com.posite.modern.ui.counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.posite.modern.ui.theme.ModernTheme

class CounterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CounterViewModel by viewModels<CounterViewModelImpl>()
            ModernTheme {
                Counter(viewModel)
            }
        }
    }
}

@Composable
fun Counter(viewModel: CounterViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Counter: ${viewModel.count.value}")
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Button(onClick = { viewModel.increment() }) {
                Text(text = "Increment")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.decrement() }) {
                Text(text = "Decrement")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CounterPreview() {
    ModernTheme {
        val viewModel: CounterViewModel = CounterViewModelImpl()
        Counter(viewModel)
    }
}