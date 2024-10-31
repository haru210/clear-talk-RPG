package com.example.cleartalkrpg.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleartalkrpg.R
import kotlinx.serialization.Serializable

@Entity(tableName = "scenarios")
data class Scenario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val jacketImage: Int = R.drawable.ic_launcher_foreground,
    val timeRequired: Int,
    var highScore: Int = 0,
    val screens: MutableList<Screen>
)

data class Screen(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val characterName: String? = null,
    var characterSpriteLeft: Int? = null,
    var characterSpriteMiddle: Int? = null,
    var characterSpriteRight: Int? = null,
    val backgroundImage: Int = R.mipmap.scenario1_image,
    val voiceOver: Int = 0,
    val line: String = "セリフを入力してください",
    val lineLength: Int = 0,
    val isRecordingRequired: Boolean = false
)
