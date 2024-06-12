package com.posite.modern.data.repository.wish

import com.posite.modern.data.local.dao.WishDao
import com.posite.modern.data.local.entity.WishEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WishRepository @Inject constructor(private val wishDao: WishDao) {
    suspend fun addWish(wish: WishEntity) {
        wishDao.addWish(wish)
    }

    fun getWishes() = flow {
        wishDao.getAllWishes().collect {
            emit(it)
        }
    }

    fun getWishById(id: Long) = flow {
        wishDao.getWishById(id).collect {
            emit(it)
        }
    }

    suspend fun updateWish(wish: WishEntity) {
        wishDao.updateWish(wish)
    }

    suspend fun deleteWish(wish: WishEntity) {
        wishDao.deleteWish(wish)
    }
}