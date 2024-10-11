package com.example.cleartalkrpg.scenarioscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.cleartalkrpg.database.Scenario
import com.example.cleartalkrpg.ui.theme.PauseIcon
import com.example.cleartalkrpg.viewmodel.ScenarioViewModel
import com.example.cleartalkrpg.R
import kotlinx.coroutines.delay

/* データベースから選択されたシナリオに必要な情報をフェッチし、シナリオ画面を開始する */
@Composable
fun ScenarioScreen(
    navController: NavController,
    scenarioViewModel: ScenarioViewModel,
    selectedScenarioId: Int,
    resultScoresState: MutableState<Map<String, Double>>,
    resultCommentState: MutableState<String>
) {
    val scenarios by scenarioViewModel.allScenarios.observeAsState(mutableListOf())
    val currentScenario = scenarios[selectedScenarioId]

    var isPaused by remember { mutableStateOf(false) }
    var messageDisplaySpeed: Long = 50
    var displayMessage by remember { mutableStateOf("") }
    var isMessageComplete by remember { mutableStateOf(false) }
    var currentScreenIndex by remember { mutableStateOf(0) }
    /* speedScore, clarityScore, volumeScore格納用のTriple */
    val partialScores by remember { mutableStateOf<Triple<MutableList<Int>, MutableList<Int>, MutableList<Int>>>(Triple(
        mutableListOf(), mutableListOf(), mutableListOf()
    ))}

    /* TODO: シナリオに画面がない場合のエラー処理。非同期処理によって上手く動作しない。非同期でthrow-catchをする方法を模索する */
    if (currentScenario.screens.isEmpty()) {
        val errorMessage = "Fatal Error: シナリオに画面が挿入されていません。"
        ScenarioHasNoScreenError(onClick = {
            navController.navigate(ClearTalkRPGScreen.SelectScenario.name)
        }, errorMessage = errorMessage)
    }

    startListening(currentScenarioIndex = currentScreenIndex, currentScenario = currentScenario, partialScores = partialScores)

    Surface(
        onClick = {
            if (isMessageComplete) {
                if (currentScreenIndex < currentScenario.screens.size - 1) {
                    currentScreenIndex++
                } else {
                    /* TODO: 録音に失敗しても最終的な値がNaNにならないような処理を行う */
                    val averageSpeedScore = partialScores.first.average()
                    val averageClarityScore = partialScores.second.average()
                    val averageVolumeScore = partialScores.third.average()
                    val totalScore = averageSpeedScore + averageClarityScore + averageVolumeScore
                    val scores = mapOf(
                        Pair("totalScore", totalScore),
                        Pair("speedScore", averageSpeedScore),
                        Pair("clarityScore", averageClarityScore),
                        Pair("volumeScore", averageVolumeScore)
                    )
                    resultScoresState.value = scores
                    navController.navigate(ClearTalkRPGScreen.Result.name)
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = currentScenario.title, modifier = Modifier.padding(8.dp))
            ScenarioScreenBackgroundImage(currentScenario.screens[currentScreenIndex].backgroundImage)
            ScenarioCharacterSprite(currentScenario.screens[currentScreenIndex].characterSprite)
            PauseButton(onClick = { isPaused = !isPaused })
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 24.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                ScenarioMessageBox(
                    scenarioMessage = if (currentScreenIndex < currentScenario.screens.size) {
                        currentScenario.screens[currentScreenIndex].line
                    } else { "" },
                    scenarioCharacterName = currentScenario.screens[currentScreenIndex].characterName,
                    messageDisplaySpeed = messageDisplaySpeed,
                    onMessageComplete = { isMessageComplete = true },
                    isMessageDisplayed = displayMessage
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                if(isPaused) {
                    PauseMenu(
                        onRestartClick = { navController.navigate(ClearTalkRPGScreen.Scenario.name) },
                        onBackToScenarioSelectScreenClick = { navController.navigate(ClearTalkRPGScreen.SelectScenario.name) },
                        onResumeClick = { isPaused = !isPaused }
                    )
                }
            }
        }
    }
}

/* シナリオに問題がある場合のエラー画面 */
@Composable
fun ScenarioHasNoScreenError(onClick: () -> Unit, errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(300.dp, 200.dp)
                .align(Alignment.Center)
                .background(Color.White)
        ) {
            Text(
                text = errorMessage,
                fontFamily = FontFamily(Font(R.font.koruri_regular)),
                color = Color.Black
            )
        }
    }
}

/* 音声録音 */
@Composable
fun startListening(
    currentScenarioIndex: Int,
    currentScenario: Scenario,
    partialScores: Triple<MutableList<Int>, MutableList<Int>, MutableList<Int>>
) {
    val context = LocalContext.current
    val recogManager = remember { SpeechRecognizerManager(context) }

    var speedScore by remember { mutableStateOf(0) }
    var clarityScore by remember { mutableStateOf(0) }
    var volumeScore by remember { mutableStateOf(0) }

    LaunchedEffect(currentScenarioIndex) {
        recogManager.setOnResultListener { results ->
            speedScore = results.first
            clarityScore = results.second
            volumeScore = results.third
            partialScores.first.add(speedScore)
            partialScores.second.add(clarityScore)
            partialScores.third.add(volumeScore)
        }
    }
    recogManager.startListening()
}

/* ポーズボタン */
@Composable
fun PauseButton(onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            onClick = onClick,
            color = Color.Gray,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.TopEnd)
                .clip(RoundedCornerShape(32.dp))
        ) {
            PauseIcon(iconColor = Color.White, iconSize = 48.dp)
        }
    }
}

/* シナリオ本編の背景画面 */
@Composable
fun ScenarioScreenBackgroundImage(backgroundImageId: Int) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = backgroundImageId),
            contentDescription = "Title screen background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}

/* 画面に表示するキャラクターのアバター */
@Composable
fun ScenarioCharacterSprite(characterSpriteId: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = characterSpriteId),
            contentDescription = "Character Sprite",
            contentScale = ContentScale.FillBounds
        )
    }
}

/* ネームプレートと表示する文字列をまとめたコンポーネント */
@Composable
fun ScenarioMessageBox(
    scenarioMessage: String,
    scenarioCharacterName: String,
    messageDisplaySpeed: Long,
    onMessageComplete: () -> Unit,
    isMessageDisplayed: String
) {
    Column(

    ) {
        ScenarioCharacterNamePlate(characterName = scenarioCharacterName)
        DisplayScenarioMessage(
            scenarioMessage = scenarioMessage,
            messageDisplaySpeed = messageDisplaySpeed,
            onMessageComplete = onMessageComplete,
            isMessageDisplayed = isMessageDisplayed
        )
    }
}

/* テキストボックスの右下に表示される逆三角形のアニメーション */
@Composable
fun AnimatedTriangleIndicator(isMessageComplete: Boolean) {

}

/* テキストボックスの上方に設置されるキャラクターのネームプレート */
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
            fontFamily = FontFamily(Font(R.font.koruri_bold)),
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp, 4.dp)
        )
    }
}

/* 渡された文字列を指定された速度で1文字ずつ表示する */
@Composable
fun DisplayScenarioMessage(
    scenarioMessage: String,
    messageDisplaySpeed: Long,
    onMessageComplete: () -> Unit,
    isMessageDisplayed: String
) {
    var displayedMessage by remember { mutableStateOf("") }

    // LaunchedEffectで文字を1文字ずつ表示する
    LaunchedEffect(scenarioMessage) {
        if (scenarioMessage.isNotEmpty()) {
            displayedMessage = ""
            for (i in scenarioMessage.indices) {
                delay(messageDisplaySpeed)
                displayedMessage += scenarioMessage[i]
            }
            onMessageComplete()
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
            fontFamily = FontFamily(Font(R.font.grsgor_r_web, FontWeight.Bold)),
            fontSize = 18.sp,
            modifier = Modifier.padding(28.dp, 12.dp)
        )
    }
}
