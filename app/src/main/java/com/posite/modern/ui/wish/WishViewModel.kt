package com.posite.modern.ui.wish

import androidx.compose.runtime.State
import com.posite.modern.data.local.entity.WishEntity

interface WishViewModel {
    val wishTitle: State<String>
    val wishDescription: State<String>
    val wishes: State<List<WishEntity>>
    val currentWish: State<WishEntity>

    fun wishTitleChanged(title: String)
    fun wishDescriptionChanged(description: String)
    fun addWish(wish: WishEntity)
    fun getWishes()
    fun getWishById(id: Long)
    fun updateWish(wish: WishEntity)
    fun deleteWish(wish: WishEntity)
}