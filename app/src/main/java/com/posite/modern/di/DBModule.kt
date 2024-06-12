package com.posite.modern.di

import android.content.Context
import androidx.room.Room
import com.posite.modern.data.local.dao.WishDao
import com.posite.modern.data.local.db.WishDB
import com.posite.modern.data.repository.wish.WishRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    @Singleton
    @Provides
    fun provideWishDatabase(
        @ApplicationContext context: Context
    ): WishDB = Room.databaseBuilder(
        context,
        WishDB::class.java,
        "wish_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideWishDao(wishDB: WishDB): WishDao = wishDB.wishDao()

    @Singleton
    @Provides
    fun provideWishRepository(wishDao: WishDao) = WishRepository(wishDao)

}