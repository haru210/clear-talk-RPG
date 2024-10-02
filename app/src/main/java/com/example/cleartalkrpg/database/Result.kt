package com.example.cleartalkrpg.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "results")
data class Result(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val scenario_title: String = "",
    val total_score: Int,
    val volume_score: Int,
    val clarity_score: Int,
    val speed_score: Int,
    val comment: String = "",
    val created_at: Date = Date()
)
