package com.posite.modern.ui.music

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MusicNavigation(
    viewModel: MusicViewModel,
    navController: NavHostController,
    drawerState: DrawerState
) {
    NavHost(navController = navController, startDestination = MusicScreen.Home.route) {
        composable(MusicScreen.Home.route) {
            MusicHomeScreen(viewModel, navController, drawerState)
        }
        /*composable(
            WishScreen.AddWish.route + "/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
                defaultValue = 0L
                nullable = false
            })
        ) { entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            UpdateWishScreen(id = id, viewModel = viewModel, navController)
        }*/
    }
}