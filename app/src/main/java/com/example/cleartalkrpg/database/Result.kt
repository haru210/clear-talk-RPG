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
)/* {
    constructor(
        scenario_title: String,
        total_score: Int,
        volume_score: Int,
        clarity_score: Int,
        speed_score: Int,
        comment: String,
        created_at: Date,
    ):
        this(
            Int.MIN_VALUE,
            scenario_title,
            total_score,
            volume_score,
            clarity_score,
            speed_score,
            comment
        )
} */
