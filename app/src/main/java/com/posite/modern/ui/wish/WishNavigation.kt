package com.posite.modern.ui.wish

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun WishNavigation(viewModel: WishViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = WishScreen.WishList.route) {
        composable(WishScreen.WishList.route) {
            WishListScreen(viewModel, navController)
        }
        composable(WishScreen.AddWish.route) {
            UpdateWishScreen(id = 0L, viewModel = viewModel, navController)
        }
    }
}