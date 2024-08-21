package com.example.cleartalkrpg.titlescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGTheme

@Composable
fun TitleScreen(name: String) {
    Text(
        text = "Good evening $name!",
        modifier = Modifier.padding(24.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    ClearTalkRPGTheme {
        TitleScreen("test")
    }
}