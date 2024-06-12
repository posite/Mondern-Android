package com.posite.modern.ui.wish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.posite.modern.ui.theme.ModernTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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


/*
@Composable
@Preview(showBackground = true)
fun WishItemPreview() {
    ModernTheme {
        WishNavigation(WishViewModelImpl(), rememberNavController())
    }
}*/
