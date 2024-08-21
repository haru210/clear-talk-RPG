package com.example.cleartalkrpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cleartalkrpg.titlescreen.TitleScreen
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClearTalkRPGTheme {
                TitleScreen(name = "Android")
            }
        }
    }
}
