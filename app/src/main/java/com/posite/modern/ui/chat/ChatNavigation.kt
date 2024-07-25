package com.posite.modern.ui.chat

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.posite.modern.ui.chat.auth.ChatAuthContractViewModel
import com.posite.modern.ui.chat.auth.LoginScreen
import com.posite.modern.ui.chat.auth.SignUpScreen
import com.posite.modern.ui.chat.chat.ChatScreen
import com.posite.modern.ui.chat.chat.ChatViewModel
import com.posite.modern.ui.chat.room.ChatRoomContractViewModel
import com.posite.modern.ui.chat.room.ChatRoomsScreen

@Composable
fun ChatNavigation(
    chatAuthContractViewModel: ChatAuthContractViewModel,
    chatRoomContractViewModel: ChatRoomContractViewModel,
    chatViewModel: ChatViewModel,
    navController: NavHostController,
    setBackPressedCallback: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ChatScreens.LoginScreen.route
    ) {
        composable(ChatScreens.SignupScreen.route) {
            SignUpScreen(
                viewModel = chatAuthContractViewModel,
                onNavigationToLogin = { navController.navigate(ChatScreens.LoginScreen.route) },
                onSignUpSuccess = {
                    navController.navigate(ChatScreens.ChatRoomsScreen.route)
                    navController.popBackStack(ChatScreens.SignupScreen.route, true)
                }
            )
        }
        composable(ChatScreens.LoginScreen.route) {
            LoginScreen(
                viewModel = chatAuthContractViewModel,
                onNavigationToSignUp = { navController.navigate(ChatScreens.SignupScreen.route) },
                onLoginSuccess = {
                    navController.navigate(ChatScreens.ChatRoomsScreen.route)
                    navController.popBackStack(ChatScreens.LoginScreen.route, true)
                }
            )
        }
        composable(ChatScreens.ChatRoomsScreen.route) {
            setBackPressedCallback()
            ChatRoomsScreen(viewModel = chatRoomContractViewModel) {
                navController.navigate("${ChatScreens.ChatScreen.route}/${it.id}")
            }
        }
        composable("${ChatScreens.ChatScreen.route}/{roomId}") { backStackEntry ->
            val roomId =
                backStackEntry.arguments?.getString("roomId") ?: ""
            ChatScreen(roomId = roomId, chatViewModel) {
                navController.navigateUp()
            }
        }
    }
}