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
import com.example.cleartalkrpg.database.CharacterSheet
import com.example.cleartalkrpg.viewmodel.ResultViewModel
import com.example.cleartalkrpg.database.Result
import com.example.cleartalkrpg.database.Screen
import com.example.cleartalkrpg.ui.theme.DownpointingTriangleAnimation
import com.example.cleartalkrpg.utils.ScreenPosition
import kotlinx.coroutines.delay

/* データベースから選択されたシナリオに必要な情報をフェッチし、シナリオ画面を開始する */
@Composable
fun ScenarioScreen(
    navController: NavController,
    scenarioViewModel: ScenarioViewModel,
    resultViewModel: ResultViewModel,
    resultState: MutableState<Result?>,
    scenarioUpdateState: MutableState<Scenario?>,
    selectedScenarioId: Int,
    resultScoresState: MutableState<Map<String, Double>>,
    resultCommentState: MutableState<String>,
    selectedCharacterSheet: CharacterSheet
) {
    /* 選択されたシナリオをcurrentScenarioに設定する */
    val scenarios by scenarioViewModel.allScenarios.observeAsState(mutableListOf())
    val currentScenario = scenarios.getOrNull(selectedScenarioId)
    var currentScreenIndex by remember { mutableIntStateOf(0) }

    /* ポーズ画面の状態管理 */
    var isPaused by remember { mutableStateOf(false) }

    /* メッセージの表示速度 */
    val messageDisplaySpeed: Long = 50

    /* メッセージが表示しきっているかどうか */
    var isMessageComplete by remember { mutableStateOf(false) }

    /* speedScore, clarityScore, volumeScore格納用のTriple */
    val partialScores by remember { mutableStateOf<Triple<MutableList<Int>, MutableList<Int>, MutableList<Int>>>(Triple(
        mutableListOf(), mutableListOf(), mutableListOf()
    ))}

    /* エラーハンドリング */
    var isScenarioError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
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

    if (currentScenario.screens[currentScreenIndex].isRecordingRequired) {
        StartListening(currentScenarioIndex = currentScreenIndex, currentScreen = currentScenario.screens[currentScreenIndex], partialScores = partialScores)
    }

    if (currentScenario.screens[currentScreenIndex].isSelectedCharacterStanding) {
        currentScenario.screens[currentScreenIndex].characterSpriteLeft = selectedCharacterSheet.sprite
    }

    Surface(
        onClick = {
            if (isScenarioError) {
                return@Surface
            }
            if (isMessageComplete) {
                isMessageComplete = false
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
                    val totalScore = averageSpeedScore + averageClarityScore + averageVolumeScore

                    val scores = mapOf(
                        Pair("totalScore", totalScore),
                        Pair("speedScore", averageSpeedScore),
                        Pair("clarityScore", averageClarityScore),
                        Pair("volumeScore", averageVolumeScore)
                    )

                    /* 総評コメントを取得 */
                    val comment = getComment(
                        scores = scores,
                        resultViewModel = resultViewModel,
                        scenarioTitle = currentScenario.title
                    )

                    resultScoresState.value = scores
                    resultCommentState.value = comment

                    /* リザルトテーブルにリザルトを挿入 */
                    val result = Result(
                        scenario_title = currentScenario.title,
                        total_score = totalScore.toInt(),
                        volume_score = averageVolumeScore.toInt(),
                        clarity_score = averageClarityScore.toInt(),
                        speed_score = averageSpeedScore.toInt(),
                        comment = comment
                    )
                    resultState.value = result

                    /* ハイスコアならばシナリオのハイスコアとして設定 */
                    if (isHighScore(totalScore, currentScenario)) {
                        currentScenario.highScore = totalScore.toInt()
                        scenarioUpdateState.value = currentScenario
                    }

                    /* リザルト画面に遷移 */
                    navController.navigate(ClearTalkRPGScreen.Result.name)
                }
            } else {
                isMessageComplete = true
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (currentScreenIndex < currentScenario.screens.size) {
                ScenarioScreenBackgroundImage(currentScenario.screens[currentScreenIndex].backgroundImage)
                currentScenario.screens[currentScreenIndex].characterSpriteLeft?.let { ScenarioCharacterSprite(characterSpriteId = it, position = ScreenPosition.Left) }
                currentScenario.screens[currentScreenIndex].characterSpriteMiddle?.let { ScenarioCharacterSprite(characterSpriteId = it, position = ScreenPosition.Middle) }
                currentScenario.screens[currentScreenIndex].characterSpriteRight?.let { ScenarioCharacterSprite(characterSpriteId = it, position = ScreenPosition.Right) }
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
                    onMessageComplete = { isMessageComplete = true },
                    isMessageComplete = isMessageComplete
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
            Text(
                text = currentScenario.title,
                fontFamily = FontFamily(Font(R.font.koruri_bold)),
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(16.dp, 8.dp)
            )
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
    currentScreen: Screen,
    partialScores: Triple<MutableList<Int>, MutableList<Int>, MutableList<Int>>
) {
    val context = LocalContext.current
    val recogManager = remember { SpeechRecognizerManager(context, currentScreen) }

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

/* 画面に表示するキャラクターのスプライト */
@Composable
fun ScenarioCharacterSprite(characterSpriteId: Int, position: ScreenPosition) {
    Box(
        contentAlignment = when {
            (position == ScreenPosition.Left) -> Alignment.CenterStart
            (position == ScreenPosition.Right) -> Alignment.CenterEnd
            else -> Alignment.Center
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(80.dp, 0.dp)
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
    isMessageComplete: Boolean
) {
    Column(

    ) {
        ScenarioCharacterNamePlate(characterName = scenarioCharacterName)
        DisplayScenarioMessage(
            scenarioMessage = scenarioMessage,
            messageDisplaySpeed = messageDisplaySpeed,
            onMessageComplete = onMessageComplete,
            isMessageComplete = isMessageComplete
        )
    }
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
    isMessageComplete: Boolean
) {
    var displayedMessage by remember { mutableStateOf("") }

    // LaunchedEffectで文字を1文字ずつ表示する
    LaunchedEffect(scenarioMessage, isMessageComplete) {
        /* もしクリックされた場合にメッセージを即座に表示する */
        if (isMessageComplete) {
            displayedMessage = scenarioMessage
        } else if (scenarioMessage.isNotEmpty()) {
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
            fontFamily = FontFamily(Font(R.font.koruri_bold, FontWeight.Bold)),
            fontSize = 18.sp,
            modifier = Modifier.padding(28.dp, 12.dp),
        )
        /* メッセージが全て表示されたあと、逆三角形のアニメーションを表示 */
        if (isMessageComplete) {
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.padding(12.dp)
            ) {
                Box(modifier = Modifier.size(18.dp)) {
                    DownpointingTriangleAnimation()
                }
            }
        }
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

fun getComment(
    scores : Map<String, Double>,
    resultViewModel: ResultViewModel,
    scenarioTitle: String
) : String {
    /* リザルトのそれぞれのスコアを取得 */
//    val totalScore = scores["totalScore"]?.toInt() ?: 0
//    val speedScore = scores["speedScore"]?.toInt() ?: 0
//    val clarityScore = scores["clarityScore"]?.toInt() ?: 0
//    val volumeScore = scores["volumeScore"]?.toInt() ?: 0
//
//    /* スコアの最大値を設定 */
//    val (maxSpeedScore, maxClarityScore, maxVolumeScore) = listOf(30, 40, 30)
//
//    /* 過去のリザルトのうち、今回と同じシナリオのリザルトの中で最も新しいものを取得する */
//    val results = resultViewModel.allResults.value
//    val sameScenarioResults = results?.filter { it.scenario_title == scenarioTitle }.orEmpty()
//    val latestSameScenarioResult = sameScenarioResults.lastOrNull()
//
//    val comment = when {
//        /* 過去のシナリオと比較して評価コメントを選択する */
//        (latestSameScenarioResult != null && latestSameScenarioResult.total_score < totalScore) -> {
//            when {
//                latestSameScenarioResult.speed_score < speedScore -> "前回より丁度の良い速度で話すことができています。その調子で頑張りましょう！"
//                latestSameScenarioResult.clarity_score < clarityScore -> "前回より聞こえやすい声で話せています。その調子で頑張りましょう！"
//                latestSameScenarioResult.volume_score < volumeScore -> "前回より大きい声が出ていて聞こえやすいです。その調子で頑張りましょう！"
//                else -> "全体的に聞こえやすい発声ができています。目の前に話相手がいると思って声が伝わるように意識すると良いでしょう。"
//            }
//        }
//
//        /* その他シナリオの評価コメント */
//        (speedScore == maxSpeedScore && clarityScore == maxClarityScore && volumeScore == maxVolumeScore) -> {
//            "完璧な発声ができています。もうここからはあなたの領域です。自由に表現力を高めてください。"
//        }
//        (maxSpeedScore - 10 <= speedScore && maxClarityScore - 10 <= clarityScore && maxVolumeScore - 10 <= volumeScore) -> {
//            "全体的に聞こえやすい発声ができています。目の前に話相手がいると思って声が伝わるように意識すると良いでしょう。"
//        }
//        (speedScore < maxSpeedScore - 20 && clarityScore < maxClarityScore - 20 && volumeScore < maxVolumeScore - 20) -> {
//            "悪くはありません。自信を持って落ち着いて話してみましょう"
//        }
//        (speedScore < maxSpeedScore - 20) -> {
//            "少し話す速さにブレを感じます。プレゼンをしている気持ちになって話してみると良いでしょう"
//        }
//        (clarityScore < maxClarityScore - 30) -> {
//            "あまり明瞭とは言えない声になってしまっています。背筋を伸ばして息を吐きながら話すとぐっと良くなると思います。"
//        }
//        (volumeScore < maxVolumeScore - 20) -> {
//            "声が小さいです。顎をひいて大きく息を吸って吐きながら話すと良い声が出るでしょう。"
//        }
//        else -> "もう少しゆっくり一言一言大切に話してみましょう！"
//    }
    return "汝、己の声が全てに届き、聴く者に響き渡ることを心得よ。"//comment
}

/* 総得点がハイスコアかどうか判定する */
fun isHighScore(
    totalScore: Double,
    currentScenario: Scenario
): Boolean {
    val isHighScore: Boolean = when {
        currentScenario.highScore == 0 -> true
        currentScenario.highScore < totalScore -> true
        else -> false
    }
    return isHighScore
}
