package com.posite.modern.ui.music

import androidx.annotation.DrawableRes
import com.posite.modern.R

sealed class MusicScreen(val route: String, val title: String) {
    sealed class MusicDrawerScreens(
        val dRoute: String,
        val dTitle: String,
        @DrawableRes val icon: Int
    ) : MusicScreen(dRoute, dTitle) {
        object Home : MusicDrawerScreens("home", "Home", R.drawable.ic_home_24)
        object Account : MusicDrawerScreens("account", "Account", R.drawable.ic_account_circle_24)
        object Subscription :
            MusicDrawerScreens("subscription", "Subscription", R.drawable.ic_subscriptions_24)

        fun DrawerList() = listOf(Home, Account, Subscription)
    }

    sealed class MusicBottomScreen(
        val bRoute: String,
        val bTitle: String,
        @DrawableRes val icon: Int
    ) : MusicScreen(bRoute, bTitle) {
        object Home : MusicBottomScreen("home", "Home", R.drawable.ic_music_home_24)
        object Browse : MusicBottomScreen("browse", "Browse", R.drawable.ic_browse_24)
        object Library :
            MusicBottomScreen("library", "Library", R.drawable.ic_library_24)

        fun BottomList() = listOf(Home, Browse, Library)
    }
}