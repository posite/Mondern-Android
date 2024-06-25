package com.posite.modern.ui.music

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MusicNavigation(
    viewModel: MusicViewModel,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MusicScreen.MusicDrawerScreens.Home.route
    ) {
        composable(MusicScreen.MusicDrawerScreens.Home.route) {
            MusicHomeView(viewModel, navController)
        }
        composable(MusicScreen.MusicBottomScreen.Home.route) {
            MusicHomeView(viewModel, navController)
        }
        composable(MusicScreen.MusicBottomScreen.Browse.route) {
            MusicBrowseView(viewModel, navController)
        }
        composable(MusicScreen.MusicBottomScreen.Library.route) {
            MusicLibraryView(viewModel, navController)
        }
        composable(MusicScreen.MusicDrawerScreens.Account.route) {
            MusicAccountView(viewModel, navController)
        }
        composable(MusicScreen.MusicDrawerScreens.Subscription.route) {
            MusicSubscriptionView(viewModel, navController)
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