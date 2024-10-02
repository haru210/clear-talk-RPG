package com.example.cleartalkrpg.utils

import androidx.room.TypeConverter
import java.util.Date

class DateTimeConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
