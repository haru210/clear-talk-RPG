package com.example.cleartalkrpg.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cleartalkrpg.utils.DateTimeConverter
import com.example.cleartalkrpg.database.Result
import com.example.cleartalkrpg.database.ResultDao

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultDao
}
