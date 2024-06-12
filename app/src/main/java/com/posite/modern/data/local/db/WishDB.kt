package com.posite.modern.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.posite.modern.data.local.dao.WishDao
import com.posite.modern.data.local.entity.DateConverter
import com.posite.modern.data.local.entity.WishEntity

@Database(
    entities = [WishEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class WishDB : RoomDatabase() {
    abstract fun wishDao(): WishDao


}