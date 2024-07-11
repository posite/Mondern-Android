package com.posite.modern.ui.wish

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.local.entity.WishEntity
import com.posite.modern.data.repository.wish.WishRepository
import com.posite.modern.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishContractViewModel @Inject constructor(private val repository: WishRepository) :
    BaseViewModel<WishContract.Event, WishContract.State, WishContract.Effect>() {
    override fun createInitialState(): WishContract.State {
        return WishContract.State(
            WishContract.WishList.Wishes(emptyList()), WishContract.WishList.SingleWish(
                WishEntity.getEmpty()
            )
        )
    }

    override fun handleEvent(event: WishContract.Event) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is WishContract.Event.AddWish -> {
                    repository.addWish(event.wish)
                    repository.getWishes().collect {
                        setState {
                            copy(wishList = WishContract.WishList.Wishes(it))
                        }
                    }
                }

                is WishContract.Event.DeleteWish -> {
                    repository.deleteWish(event.wish)
                    repository.getWishes().collect {
                        setState {
                            copy(wishList = WishContract.WishList.Wishes(it))
                        }
                    }
                }

                is WishContract.Event.UpdateWish -> {
                    repository.updateWish(event.wish)
                    repository.getWishes().collect {
                        setState {
                            copy(wishList = WishContract.WishList.Wishes(it))
                        }
                    }
                }

                is WishContract.Event.GetSingleWish -> {
                    //Log.d("WishContractViewModel", "${event.id}")
                    repository.getWishById(event.id).collect {
                        setState {
                            copy(wish = WishContract.WishList.SingleWish(it))
                        }
                    }
                }

                is WishContract.Event.WishDescriptionChanged -> {
                    setState {
                        copy(wish = currentState.wish.copy(wish.wish.copy(description = event.description)))
                    }
                }

                is WishContract.Event.WishTitleChanged -> {
                    setState {
                        copy(wish = currentState.wish.copy(wish.wish.copy(title = event.title)))
                    }
                }

                is WishContract.Event.GetWishes -> {
                    repository.getWishes().collect { wishes ->
                        setState {
                            copy(wishList = WishContract.WishList.Wishes(wishes))
                        }
                    }
                }

                is WishContract.Event.ShowWishBlankToast -> {
                    setEffect { WishContract.Effect.ShowWishBlankToast }
                }

                is WishContract.Event.ClearWish -> {
                    setState {
                        copy(wish = WishContract.WishList.SingleWish(WishEntity.getEmpty()))
                    }
                    Log.d("WishContractViewModel", "${currentState.wish.wish}")
                }
            }
        }
    }

    fun addWish(wish: WishEntity) {
        setEvent(WishContract.Event.AddWish(wish))
    }

    fun deleteWish(wish: WishEntity) {
        setEvent(WishContract.Event.DeleteWish(wish))
    }

    fun updateWish(wish: WishEntity) {
        setEvent(WishContract.Event.UpdateWish(wish))
    }

    fun getSingleWish(id: Long) {
        setEvent(WishContract.Event.GetSingleWish(id))
    }

    fun wishDescriptionChanged(description: String) {
        setEvent(WishContract.Event.WishDescriptionChanged(description))
    }

    fun wishTitleChanged(title: String) {
        setEvent(WishContract.Event.WishTitleChanged(title))
    }

    fun getWishes() {
        setEvent(WishContract.Event.GetWishes)
    }

    fun showWishBlankToast() {
        setEvent(WishContract.Event.ShowWishBlankToast)
    }

    fun clearWish() {
        setEvent(WishContract.Event.ClearWish)
    }
}