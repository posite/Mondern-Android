package com.posite.modern.ui.chat

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.posite.modern.ui.chat.auth.ChatAuthViewModel
import com.posite.modern.ui.chat.auth.LoginScreen
import com.posite.modern.ui.chat.auth.SignUpScreen
import com.posite.modern.ui.chat.room.ChatRoomScreen
import com.posite.modern.ui.chat.room.ChatRoomViewModel

@Composable
fun ChatNavigation(
    chatAuthViewModel: ChatAuthViewModel,
    chatRoomViewModel: ChatRoomViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ChatScreens.LoginScreen.route
    ) {
        composable(ChatScreens.SignupScreen.route) {
            SignUpScreen(
                viewModel = chatAuthViewModel,
                onNavigationToLogin = { navController.navigate(ChatScreens.LoginScreen.route) },
                onSignUpSuccess = { navController.navigate(ChatScreens.ChatRoomsScreen.route) }
            )
        }
        composable(ChatScreens.LoginScreen.route) {
            LoginScreen(
                viewModel = chatAuthViewModel,
                onNavigationToSignUp = { navController.navigate(ChatScreens.SignupScreen.route) },
                onLoginSuccess = { navController.navigate(ChatScreens.ChatRoomsScreen.route) }
            )
        }
        composable(ChatScreens.ChatRoomsScreen.route) {
            ChatRoomScreen(
                viewModel = chatRoomViewModel
            )
        }
    }
}