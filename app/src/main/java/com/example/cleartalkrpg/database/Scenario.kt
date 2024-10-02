package com.example.cleartalkrpg.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scenarios")
data class Scenario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val jacketImage: Int,
    val timeRequired: Int,
    val highScore: Int = 0
)
