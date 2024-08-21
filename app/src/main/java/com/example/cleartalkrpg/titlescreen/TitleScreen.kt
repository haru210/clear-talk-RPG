package com.example.cleartalkrpg.titlescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGTheme

@Composable
fun TitleScreen(name: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.title_screen_background_image),
            contentDescription = "Title screen background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
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