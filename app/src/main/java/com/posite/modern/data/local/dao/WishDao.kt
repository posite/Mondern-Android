package com.posite.modern.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.posite.modern.data.local.entity.WishEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addWish(wish: WishEntity)

    @Query("SELECT * FROM  wish_table")
    suspend fun getWishes(): Flow<List<WishEntity>>

    @Query("SELECT * FROM wish_table WHERE id = :id")
    suspend fun getWishById(id: Long): Flow<WishEntity>

    @Update
    suspend fun updateWish(wish: WishEntity)

    @Delete
    suspend fun deleteWish(wish: WishEntity)
}