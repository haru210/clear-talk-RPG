package com.example.cleartalkrpg.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "results")
data class Result(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val scenario_title: String = "",
    val total_score: Float,
    val volume_score: Float,
    val clarity_score: Float,
    val speed_score: Float,
    val comment: String = "",
    val created_at: Date = Date()
) {
    constructor(
        scenario_title: String,
        total_score: Float,
        volume_score: Float,
        clarity_score: Float,
        speed_score: Float,
        comment: String
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
}
