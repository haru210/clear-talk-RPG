package com.example.cleartalkrpg.utils

import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import com.example.cleartalkrpg.database.Result
import com.example.cleartalkrpg.database.Scenario
import com.example.cleartalkrpg.database.ResultDao
import com.example.cleartalkrpg.database.ScenarioDao

@Database(entities = [(Result::class), (Scenario::class)], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, ScreenListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultDao
    abstract fun scenarioDao(): ScenarioDao
}
