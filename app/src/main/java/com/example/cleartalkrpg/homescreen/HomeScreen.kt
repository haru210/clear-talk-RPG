package com.example.cleartalkrpg.homescreen

import android.Manifest.permission.RECORD_AUDIO
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.database.CharacterSheet
import com.example.cleartalkrpg.ui.theme.AccessibilityIcon
import com.example.cleartalkrpg.ui.theme.HistoryIcon
import com.example.cleartalkrpg.ui.theme.PersonAddIcon

@Composable
fun HomeScreen(
    navController: NavController,
    selectedCharacterSheet: CharacterSheet
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeScreenBackgroundImage()
        selectedCharacterSheet.sprite?.let { HomeCharacterSprite(characterSprite = it) }
        DarkeningBottomScreen()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(80.dp, 40.dp)
        ) {
            HomeMenu(navController = navController)
            StartToAdventure(navController = navController)
        }
    }
}

/* 選択状態のキャラクターのスプライトを表示する */
@Composable
fun HomeCharacterSprite(characterSprite: Int) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxSize()
            .padding(100.dp, 0.dp)
    ) {
        Image(
            painter = painterResource(id = characterSprite),
            contentDescription = "Home Character Sprite"
        )
    }
}

/* 画面の一部を下部に向かって暗くする */
@Composable
fun DarkeningBottomScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.DarkGray
                    ),
                    startY = 700f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    )
}

/* メニュー */
@Composable
fun HomeMenu(navController: NavController) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            /* キャラメイクボタン */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { navController.navigate(ClearTalkRPGScreen.CreateCharacterSheet.name) }
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                PersonAddIcon(iconColor = Color.Black, iconSize = 48.dp)
                Text(
                    text = "キャラメイク",
                    fontFamily = FontFamily(Font(R.font.hangyaku)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight(1000)
                )
            }
            /* キャラセレクトボタン */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { navController.navigate(ClearTalkRPGScreen.SelectCharacter.name) }
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                AccessibilityIcon(iconColor = Color.Black, iconSize = 48.dp)
                Text(
                    text = "キャラセレクト",
                    fontFamily = FontFamily(Font(R.font.hangyaku)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight(1000)
                )
            }
            /* リザルトヒストリーボタン */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { navController.navigate(ClearTalkRPGScreen.ResultHistory.name) }
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                HistoryIcon(iconColor = Color.Black, iconSize = 48.dp)
                Text(
                    text = "リザルトヒストリー",
                    fontFamily = FontFamily(Font(R.font.hangyaku)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight(1000)
                )
            }
        }
    }
}

/* 背景 */
@Composable
fun HomeScreenBackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.title_screen_background_image),
            contentDescription = "Home Screen Background Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}

/* シナリオ選択画面へ移動するボタン */
@Composable
fun StartToAdventure(navController: NavController) {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->

        }
    )

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(200.dp, 150.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.zabuton_notebook),
                contentDescription = "A Book to the journey",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.clickable {
                    permissionLauncher.launch(RECORD_AUDIO)
                    navController.navigate(ClearTalkRPGScreen.SelectScenario.name)
                }
            )
            Text(
                text = "冒険を始める",
                fontFamily = FontFamily(Font(R.font.hangyaku)),
                fontSize = 40.sp,
                fontWeight = FontWeight(1000)
            )
        }
    }
}
