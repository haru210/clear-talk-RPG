package com.example.cleartalkrpg.titlescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ClearTalkRPGScreen

@Composable
fun TitleScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .clickable { navController.navigate(ClearTalkRPGScreen.Home.name) }
    ) {
        TitleScreenBackgroundImage()
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleLogo()
                TapToStart()
            }
        }
    }
}

/* タイトルロゴ */
@Composable
fun TitleLogo() {
    Box {
        Text(
            text = "ClearTalk RPG",
            fontFamily = FontFamily(Font(R.font.hangyaku)),
            fontSize = 100.sp,
            color = Color.White,
            fontWeight = FontWeight(1000)
        )
        Text(
            text = "ClearTalk RPG",
            fontFamily = FontFamily(Font(R.font.hangyaku)),
            fontSize = 100.sp,
            color = Color.Black
        )
    }
}

/* タイトルロゴ下部の画面タップを促すテキスト */
@Composable
fun TapToStart() {
    Box {
        Text(
            text = "tap to start",
            fontFamily = FontFamily(Font(R.font.hangyaku)),
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight(1000)
        )
        Text(
            text = "tap to start",
            fontFamily = FontFamily(Font(R.font.hangyaku)),
            fontSize = 30.sp,
            color = Color.Black
        )
    }
}

/* 背景 */
@Composable
fun TitleScreenBackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.title_screen_background_image),
            contentDescription = "Title Screen Background Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}
