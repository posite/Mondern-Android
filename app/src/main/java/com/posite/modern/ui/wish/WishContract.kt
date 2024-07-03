package com.posite.modern.ui.wish

import com.posite.modern.data.local.entity.WishEntity
import com.posite.modern.ui.base.UiEffect
import com.posite.modern.ui.base.UiEvent
import com.posite.modern.ui.base.UiState

class WishContract {
    sealed class Event : UiEvent {
        data class AddWish(val wish: WishEntity) : Event()
        data class DeleteWish(val wish: WishEntity) : Event()
        data class UpdateWish(val wish: WishEntity) : Event()
        object GetWishes : Event()
        data class GetSingleWish(val id: Long) : Event()
        data class WishTitleChanged(val title: String) : Event()
        object ShowWishBlankToast : Event()
        data class WishDescriptionChanged(val description: String) : Event()
    }

    sealed class WishList {
        data class Wishes(val wishList: List<WishEntity>) : WishList()
        data class SingleWish(val wish: WishEntity) : WishList()
    }

    sealed class Effect : UiEffect {
        object ShowWishBlankToast : Effect()
    }

    data class State(val wishList: WishList.Wishes, val wish: WishList.SingleWish) : UiState
}