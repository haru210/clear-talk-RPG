package com.example.cleartalkrpg.titlescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ui.theme.HistoryIcon

/* タイトル画面を表示する関数 */
@Composable
fun TitleScreen(navController: NavController) {
    TitleScreenBackgroundImage()
    TitleLogo()
    TitleScreenMenu(navController)
}

/* タイトル画面の背景を表示する関数 */
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

/* タイトルロゴを表示する関数 */
@Composable
fun TitleLogo() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            color = Color.Gray.copy(alpha = 0.65f),
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 120.dp)
                .clip(RoundedCornerShape(8.dp))
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

/* メニュー (選択画面への移動ボタンと履歴確認画面への移動ボタン) を表示する関数 */
@Composable
fun TitleScreenMenu(navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 120.dp, 0.dp, 0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TapToStartButton(navController)
            ViewResultHistoryButton()
        }
    }
}

/* 選択画面への移動ボタンを表示する関数 */
@Composable
fun TapToStartButton(navController: NavController) {
    Surface(
        onClick = {
            navController.navigate(ClearTalkRPGScreen.SelectScenario.name) // 選択画面へ移動する
        },
        color = Color.Gray.copy(alpha = 0.65f),
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
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

/* 履歴確認画面への移動ボタンを表示する関数 */
@Composable
fun ViewResultHistoryButton() {
    Surface(
        onClick = {},
        color = Color.Gray.copy(alpha = 0.65f),
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier.padding(16.dp, 0.dp)
        ) {
            Row(
            ) {
                HistoryIcon(iconColor = Color.White, iconSize = 48.dp)
                Text(
                    text = "リザルト履歴確認",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(12.dp, 12.dp)
                )
            }
        }
    }
}
