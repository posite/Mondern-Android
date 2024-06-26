package com.posite.modern.ui.chat

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun ChatNavigation(
    viewModel: ChatAuthViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ChatScreens.SignupScreen.route
    ) {
        composable(ChatScreens.SignupScreen.route) {
            SignUpScreen(
                viewModel = viewModel,
                onNavigationToLogin = { navController.navigate(ChatScreens.LoginScreen.route) }
            )
        }
        composable(ChatScreens.LoginScreen.route) {
            LoginScreen(
                viewModel = viewModel,
                onNavigationToSignUp = { navController.navigate(ChatScreens.SignupScreen.route) }
            )
        }
    }
}