package com.example.cleartalkrpg.scenarioscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.cleartalkrpg.resultscreen.getComment
import kotlinx.coroutines.delay

/* TODO: シナリオ終了時にデータベースにリザルトを保存する */
/* データベースから選択されたシナリオに必要な情報をフェッチし、シナリオ画面を開始する */
@Composable
fun ScenarioScreen(
    navController: NavController,
    scenarioViewModel: ScenarioViewModel,
    selectedScenarioId: Int,
    resultScoresState: MutableState<Map<String, Double>>,
    resultCommentState: MutableState<String>
) {
    /* 選択されたシナリオをcurrentScenarioに設定する */
    val scenarios by scenarioViewModel.allScenarios.observeAsState(mutableListOf())
    val currentScenario = scenarios.getOrNull(selectedScenarioId)

    var isScenarioError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var isPaused by remember { mutableStateOf(false) }
    val messageDisplaySpeed: Long = 50
    var isMessageComplete by remember { mutableStateOf(false) }
    var currentScreenIndex by remember { mutableIntStateOf(0) }

    /* speedScore, clarityScore, volumeScore格納用のTriple */
    val partialScores by remember { mutableStateOf<Triple<MutableList<Int>, MutableList<Int>, MutableList<Int>>>(Triple(
        mutableListOf(), mutableListOf(), mutableListOf()
    ))}

    /* エラーハンドリング */
    if (currentScenario == null) {
        ScenarioError(
            onClick = { navController.navigate(ClearTalkRPGScreen.SelectScenario.name) },
            errorMessage = "Fatal Error: The scenario you selected doesn't exist."
        )
        return
    }

    if (currentScenario.screens.isEmpty()) {
        ScenarioError(
            onClick = { navController.navigate(ClearTalkRPGScreen.SelectScenario.name) },
            errorMessage = "Fatal Error: There are no screens inserted in the selected scenario."
        )
        return
    }

    StartListening(currentScenarioIndex = currentScreenIndex, currentScenario = currentScenario, partialScores = partialScores)

    Surface(
        onClick = {
            if (isScenarioError) {
                return@Surface
            }
            if (isMessageComplete) {
                if (currentScreenIndex < currentScenario.screens.size - 1) {
                    currentScreenIndex++
                } else {
                    /* 録音に失敗したときの点数がNaNにならないよう排除する */
                    val averageSpeedScore = when {
                        partialScores.first.average().isNaN() -> 0.0
                        else -> partialScores.first.average()
                    }
                    val averageClarityScore = when {
                        partialScores.second.average().isNaN() -> 0.0
                        else -> partialScores.second.average()
                    }
                    val averageVolumeScore = when {
                        partialScores.third.average().isNaN() -> 0.0
                        else -> partialScores.third.average()
                    }
                    /* 総評コメントを取得 */
                    val comment = getComment(Triple(averageSpeedScore.toInt(), averageClarityScore.toInt(), averageVolumeScore.toInt()))
                    val totalScore = averageSpeedScore + averageClarityScore + averageVolumeScore
                    val scores = mapOf(
                        Pair("totalScore", totalScore),
                        Pair("speedScore", averageSpeedScore),
                        Pair("clarityScore", averageClarityScore),
                        Pair("volumeScore", averageVolumeScore)
                    )
                    resultScoresState.value = scores
                    resultCommentState.value = comment
                    navController.navigate(ClearTalkRPGScreen.Result.name)
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = currentScenario.title, modifier = Modifier.padding(8.dp))
            if (currentScreenIndex < currentScenario.screens.size) {
                ScenarioScreenBackgroundImage(currentScenario.screens[currentScreenIndex].backgroundImage)
                ScenarioCharacterSprite(currentScenario.screens[currentScreenIndex].characterSprite)
            } else {
                isScenarioError = true
                errorMessage = "There are no screens inserted in the selected scenario."
            }
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
                    onMessageComplete = { isMessageComplete = true }
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

    /* エラーが発生している場合にエラー画面を表示する */
    if (isScenarioError) {
        ScenarioError(onClick = {
            navController.navigate(ClearTalkRPGScreen.SelectScenario.name)
        }, errorMessage = errorMessage)
    }
}

/* 音声録音 */
@Composable
fun StartListening(
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
    onMessageComplete: () -> Unit
) {
    Column(

    ) {
        ScenarioCharacterNamePlate(characterName = scenarioCharacterName)
        DisplayScenarioMessage(
            scenarioMessage = scenarioMessage,
            messageDisplaySpeed = messageDisplaySpeed,
            onMessageComplete = onMessageComplete
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
    onMessageComplete: () -> Unit
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
            modifier = Modifier.padding(28.dp, 12.dp),
        )
    }
}

/* シナリオに問題がある場合のエラー画面 */
@Composable
fun ScenarioError(onClick: () -> Unit, errorMessage: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .defaultMinSize(500.dp, 150.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier.padding(24.dp, 32.dp)
            ) {
                Text(
                    text = errorMessage,
                    fontFamily = FontFamily(Font(R.font.koruri_regular)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Click to return to scenario select screen." ,
                    fontFamily = FontFamily(Font(R.font.koruri_regular)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
