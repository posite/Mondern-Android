package com.posite.modern.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "wish_table")
data class WishEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val description: String,
    val date: Date
) : Parcelable {
    companion object {
        fun getEmpty() = WishEntity(0L, "", "", Date())
    }
}

class DateConverter {
    @TypeConverter
    fun dateToString(date: Date): String {
        return date.time.toString()
    }

    @TypeConverter
    fun stringToDate(value: String): Date {
        return Date(value.toLong())
    }

}

