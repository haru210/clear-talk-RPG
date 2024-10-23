package com.example.cleartalkrpg.titlescreen

import android.Manifest.permission.RECORD_AUDIO
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ui.theme.HistoryIcon
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.ui.theme.TapEffectScreen

@Composable
fun TitleScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .clickable { navController.navigate(ClearTalkRPGScreen.Home.name) }
    ) {
        Column {
            TitleScreenBackgroundImage()
            TitleLogo()
        }
    }
}

@Composable
fun TitleLogo() {
    Text(
        text = "ClearTalk RPG",
        fontFamily = FontFamily(Font(R.font.koruri_bold)),
        fontSize = 40.sp
    )
}

@Composable
fun TapToStartButton() {
    Text(
        text = "tap to start",
        fontFamily = FontFamily(Font(R.font.koruri_bold)),
        fontSize = 16.sp
    )
}

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