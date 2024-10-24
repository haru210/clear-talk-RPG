package com.example.cleartalkrpg.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleartalkrpg.R

@Entity(tableName = "scenarios")
data class Scenario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val jacketImage: Int,
    val timeRequired: Int,
    var highScore: Int = 0,
    val screens: MutableList<Screen>
)

data class Screen(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val characterName: String = "かまきり",
    val characterSpriteLeft: Int? = null,
    val characterSpriteMiddle: Int? = null,
    val characterSpriteRight: Int? = null,
    val backgroundImage: Int = R.mipmap.scenario1_image,
    val voiceOver: Int = 0,
    val line: String = "セリフを入力してください",
    val lineLength: Int = 0,
    val isRecordingRequired: Boolean = false
)
