package com.example.cleartalkrpg.homescreen

import android.Manifest.permission.RECORD_AUDIO
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.database.CharacterSheet
import com.example.cleartalkrpg.ui.theme.HistoryIcon

@Composable
fun HomeScreen(
    navController: NavController,
    selectedCharacterSheet: CharacterSheet
) {

}

@Composable
fun TapToStartButton(navController: NavController) {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->

        }
    )
    Surface(
        onClick = {
            permissionLauncher.launch(RECORD_AUDIO)
            navController.navigate(ClearTalkRPGScreen.SelectScenario.name) // 選択画面へ移動する
        },
        color = Color.Gray.copy(alpha = 0.65f),
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = "スタート",
            fontFamily = FontFamily(Font(R.font.koruri_bold)),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(40.dp, 14.dp)
        )
    }
}

@Composable
fun ViewResultHistoryButton(navController: NavController) {
    Surface(
        onClick = {
            navController.navigate(ClearTalkRPGScreen.ResultHistory.name)
        },
        color = Color.Gray.copy(alpha = 0.65f),
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier.padding(16.dp, 0.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp, 4.dp)
            ) {
                HistoryIcon(iconColor = Color.White, iconSize = 48.dp)
                Text(
                    text = "リザルト履歴確認",
                    fontFamily = FontFamily(Font(R.font.koruri_bold)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}
