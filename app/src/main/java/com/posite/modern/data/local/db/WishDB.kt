package com.posite.modern.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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
    abstract fun bookMarkDao(): WishDao

    companion object {
        private var instance: WishDB? = null

        @Synchronized
        fun getInstance(context: Context): WishDB? {
            if (instance == null) {
                synchronized(WishDB::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WishDB::class.java,
                        "wish_db"
                    ).build()
                }
            }
            return instance
        }
    }

}