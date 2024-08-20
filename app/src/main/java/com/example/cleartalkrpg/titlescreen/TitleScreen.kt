package com.example.cleartalkrpg.titlescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TitleScreen(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Good evening $name!",
        modifier = modifier
    )
}