package com.posite.modern.ui.wish

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun WishNavigation(viewModel: WishContractViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = WishScreen.WishList.route) {
        composable(WishScreen.WishList.route) {
            WishListScreen(viewModel, navController)
        }
        composable(
            WishScreen.AddWish.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
                defaultValue = 0L
                nullable = false
            })
        ) { entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            UpdateWishScreen(id = id, viewModel = viewModel, navController)
        }
    }
}