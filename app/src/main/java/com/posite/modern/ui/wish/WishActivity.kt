package com.posite.modern.ui.wish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.posite.modern.data.remote.model.wish.WishModel
import com.posite.modern.ui.theme.ModernTheme

class WishActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModernTheme {
                val viewModel: WishViewModel by viewModels<WishViewModelImpl>()
                WishNavigation(viewModel, rememberNavController())
            }
        }

    }
}


@Composable
@Preview(showBackground = true)
fun WishItemPreview() {
    ModernTheme {
        WishNavigation(WishViewModelImpl(), rememberNavController())
    }
}