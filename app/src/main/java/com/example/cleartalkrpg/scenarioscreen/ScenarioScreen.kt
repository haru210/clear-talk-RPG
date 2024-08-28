package com.example.cleartalkrpg.scenarioscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.R
import kotlinx.coroutines.delay

@Composable
fun ScenarioScreen(navController: NavController, selectedScenarioId: Int) {
    Surface(
        onClick = {
            navController.navigate(ClearTalkRPGScreen.Result.name)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ScenarioScreenBackgroundImage()
            ScenarioCharacterSprite()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 24.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                ScenarioMessageBox(
                    scenarioMessage =
                    "おう　なつだぜ\n" +
                            "おれは　げんきだぜ\n" +
                            "あまり　ちかよるな",
                    scenarioCharacterName = "かまきり"
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Pause(navController = navController)
            }
        }
    }
}

@Composable
fun ScenarioScreenBackgroundImage() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.title_screen_background_image),
            contentDescription = "Title screen background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
fun ScenarioCharacterSprite() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.kamakiri),
            contentDescription = "Kamakiri sprite",
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun ScenarioMessageBox(scenarioMessage: String, scenarioCharacterName: String) {
    Column {
        ScenarioCharacterNamePlate(characterName = scenarioCharacterName)
        DisplayScenarioMessage(scenarioMessage = scenarioMessage)
    }
}

@Composable
fun ScenarioCharacterNamePlate(characterName: String) {
    Surface(
        color = Color.Gray.copy(0.65f),
        modifier = Modifier
            .padding(12.dp, 0.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = characterName,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp, 4.dp)
        )
    }
}

@Composable
fun DisplayScenarioMessage(scenarioMessage: String) {
    var displayedMessage by remember { mutableStateOf("") }

    // LaunchedEffectで文字を1文字ずつ表示する
    LaunchedEffect(scenarioMessage) {
        displayedMessage = ""
        for (i in scenarioMessage.indices) {
            delay(50)
            displayedMessage += scenarioMessage[i]
        }
    }

    Surface(
        color = Color.Gray.copy(0.65f),
        modifier = Modifier
            .height(100.dp)
            .width(650.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Text(
            text = displayedMessage,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(28.dp, 12.dp)
        )
    }
}
