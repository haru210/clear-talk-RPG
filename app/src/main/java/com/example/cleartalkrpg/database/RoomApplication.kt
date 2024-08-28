package com.example.cleartalkrpg.database

import android.app.Application
import androidx.room.Room
import com.example.cleartalkrpg.utils.AppDatabase

class RoomApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "results"
        ).build()
    }
}