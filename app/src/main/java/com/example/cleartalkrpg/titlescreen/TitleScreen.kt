package com.example.cleartalkrpg.titlescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGTheme

@Composable
fun TitleScreen() {
    TitleScreenBackgroundImage()
    TitleLogo()
    TapToStartButton()
}

@Composable
fun TitleScreenBackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.title_screen_background_image),
            contentDescription = "Title screen background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
fun TitleLogo() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = Color.Gray.copy(alpha = 0.65f),
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 120.dp)
        ) {
            Text(
                text = "Clear Talk RPG",
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
                color = Color.White,
                modifier = Modifier.padding(36.dp, 12.dp)
            )

        }
    }
}

@Composable
fun TapToStartButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = Color.Gray.copy(alpha = 0.65f),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(0.dp, 150.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = "tap to start",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(36.dp, 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    ClearTalkRPGTheme {
        TitleScreen()
    }
}