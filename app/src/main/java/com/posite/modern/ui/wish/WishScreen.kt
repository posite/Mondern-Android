package com.posite.modern.ui.wish

sealed class WishScreen(val route: String) {
    object WishList : WishScreen("wish_list")
    object AddWish : WishScreen("add_wish")
}