package com.posite.modern.ui.music

import androidx.annotation.DrawableRes
import com.posite.modern.R

sealed class MusicScreen(val route: String, val title: String) {
    sealed class MusicDrawerScreens(
        val dRoute: String,
        val dTitle: String,
        @DrawableRes val icon: Int
    ) : MusicScreen(dRoute, dTitle) {
        object Account : MusicDrawerScreens("account", "Account", R.drawable.ic_account_circle_24)
        object Subscription :
            MusicDrawerScreens("subscription", "Subscription", R.drawable.ic_subscriptions_24)

        object AddAccount :
            MusicDrawerScreens("add_account", "Add Account", R.drawable.ic_person_add_24)

        fun DrawerList() = listOf(Account, Subscription, AddAccount)
    }

    object Home : MusicScreen("home", "Home")
}