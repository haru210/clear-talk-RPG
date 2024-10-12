package com.example.cleartalkrpg.resultscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ui.theme.darken
import com.example.cleartalkrpg.ui.theme.n_BackgroundColor
import com.example.cleartalkrpg.ui.theme.n_Blue
import com.example.cleartalkrpg.ui.theme.n_FontColor
import com.example.cleartalkrpg.ui.theme.n_BlueGradient
import com.example.cleartalkrpg.ui.theme.n_BorderColor
import com.example.cleartalkrpg.ui.theme.n_DarkGray
import com.example.cleartalkrpg.ui.theme.n_DarkGrayGradient
import com.example.cleartalkrpg.ui.theme.n_Gold
import com.example.cleartalkrpg.ui.theme.n_GoldGradient
import com.example.cleartalkrpg.ui.theme.n_Green
import com.example.cleartalkrpg.ui.theme.n_GreenGradient
import com.example.cleartalkrpg.ui.theme.n_Orange
import com.example.cleartalkrpg.ui.theme.n_OrangeGradient
import com.example.cleartalkrpg.ui.theme.n_RainbowGradient
import com.example.cleartalkrpg.ui.theme.n_Yellow
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun ResultScreen(
    navController: NavController,
    resultScores: Map<String, Double>,
    resultComment: String
) {
    /* 総合得点とそれぞれの項目の得点を各々の変数に格納 */
    val totalScore = 80.0//resultScores["totalScore"]?:0.0
    val volumeScore = resultScores["volumeScore"]?:0.0
    val clarityScore = resultScores["clarityScore"]?:0.0
    val speedScore = resultScores["speedScore"]?:0.0

    /* 総合得点に応じてTotalScoreBoardの背景色とフォントの色を変更 (first: 背景色, second: フォントの色) */
    val totalScoreBoardColor: Triple<n_BackgroundColor, n_FontColor, n_BorderColor> = when {
        totalScore == 100.0 -> Triple(n_BackgroundColor.Gradient(n_RainbowGradient), n_FontColor.SolidColor(Color.White), n_BorderColor.SolidColor(Color.White))
        totalScore >= 90.0 -> Triple(n_BackgroundColor.Gradient(n_GoldGradient), n_FontColor.SolidColor(Color.White), n_BorderColor.SolidColor(n_Gold))
        totalScore >= 80.0 -> Triple(n_BackgroundColor.Gradient(n_GreenGradient), n_FontColor.SolidColor(Color.White), n_BorderColor.SolidColor(n_Green))
        totalScore >= 70.0 -> Triple(n_BackgroundColor.Gradient(n_BlueGradient), n_FontColor.SolidColor(Color.White), n_BorderColor.SolidColor(n_Blue))
        totalScore >= 60.0 -> Triple(n_BackgroundColor.Gradient(n_OrangeGradient), n_FontColor.SolidColor(Color.Black), n_BorderColor.SolidColor(n_Orange))
        else -> Triple(n_BackgroundColor.Gradient(n_DarkGrayGradient), n_FontColor.SolidColor(n_Yellow), n_BorderColor.SolidColor(n_DarkGray))
    }

    /* 背景を表示 */
    TitleScreenBackgroundImage()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.width(IntrinsicSize.Max)
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TotalScoreBoard(totalScore = totalScore, totalScoreBoardColor = totalScoreBoardColor)
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    CommentBoard(comment = resultComment)
                }
            }
            Text(
                text = "詳細評価",
                fontFamily = FontFamily(Font(R.font.koruri_bold, FontWeight.Bold)),
                fontSize = 22.sp,
                modifier = Modifier.padding(48.dp, 0.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                PartialScoreBoard(typeName = "音量", score = volumeScore, maxScore = 30, backgroundColor = Color(244, 67, 54, 255))
                PartialScoreBoard(typeName = "明瞭さ", score = clarityScore, maxScore = 40, backgroundColor = Color(0xFFB3E5FC))
                PartialScoreBoard(typeName = "速さ", score = speedScore, maxScore = 30, backgroundColor = Color(95, 253, 101, 255))
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                BackToOtherScreenButton(
                    displayName = "タイトル画面へ",
                    backToOtherScreenClick = {
                        navController.navigate(ClearTalkRPGScreen.Title.name)
                    }
                )
                BackToOtherScreenButton(
                    displayName = "シナリオ選択へ",
                    backToOtherScreenClick = {
                        navController.navigate(ClearTalkRPGScreen.SelectScenario.name)
                    }
                )
                BackToOtherScreenButton(
                    displayName = "履歴確認画面へ",
                    backToOtherScreenClick = {
                        navController.navigate(ClearTalkRPGScreen.ResultHistory.name)
                    }
                )
            }
        }
    }
}

/* 総合得点のスコアボード */
@Composable
fun TotalScoreBoard(totalScore: Number, totalScoreBoardColor: Triple<n_BackgroundColor, n_FontColor, n_BorderColor>) {
    val backgroundColor = totalScoreBoardColor.first
    val fontColor = totalScoreBoardColor.second
    val borderColor = totalScoreBoardColor.third

    /* アニメーション用の文字列のスケール状態 (最初は非表示) */
    var scale by remember { mutableStateOf(0f) }
    var isVisible by remember { mutableStateOf(false) }

    /* 画面表示0.5秒後にアニメーション開始 */
    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
        scale = 1f
    }

    val animatedScale by animateFloatAsState(targetValue = scale, animationSpec = tween(durationMillis = 300))

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .size(200.dp, 120.dp)
            .border(
                4.dp, when (borderColor) {
                    is n_BorderColor.SolidColor -> borderColor.color
                }, shape = RoundedCornerShape(8.dp)
            )
            .then(
                when (backgroundColor) {
                    is n_BackgroundColor.SolidColor -> Modifier.background(backgroundColor.color)
                    is n_BackgroundColor.Gradient -> Modifier.background(backgroundColor.brush)
                }
            )
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box {
                if (isVisible) {
                    Text(
                        text = String.format(Locale.US, "%.1f", totalScore),
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = when(fontColor){ is n_FontColor.SolidColor -> fontColor.color },
                        modifier = Modifier
                            .offset(
                                x = 4.dp,
                                y = 2.dp
                            )
                            .alpha(0.75f)
                            .scale(animatedScale)
                    )
                    Text(
                        text = String.format(Locale.US, "%.1f", totalScore),
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = when(fontColor){ is n_FontColor.SolidColor -> fontColor.color },
                        modifier = Modifier.scale(animatedScale)
                    )
                }
            }
            Text(
                text = "点",
                fontFamily = FontFamily(Font(R.font.koruri_bold, FontWeight.Bold)),
                color = when(fontColor){ is n_FontColor.SolidColor -> fontColor.color },
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .scale(animatedScale)
            )
        }
    }
}

/* 一言コメントボード */
@Composable
fun CommentBoard(comment: String) {
    Surface(
        color = Color.LightGray.copy(alpha = 0.60f),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 0.dp, // 影や枠線を取り除く]
        tonalElevation = 0.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, Color.LightGray.darken(0.8f), RoundedCornerShape(8.dp))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(12.dp, 8.dp)
            ) {
                Text(
                    text = "【一言コメント】",
                    fontFamily = FontFamily(Font(R.font.koruri_bold, FontWeight.Bold)),
                    fontSize = 18.sp
                )
                Text(
                    text = comment,
                    fontFamily = FontFamily(Font(R.font.koruri_regular, FontWeight.Bold))
                )
            }
        }
    }
}

/* TODO: 点数がスロット形式のアニメーションで表示されるようにする */
/* 各項目の得点ボード用コンポーネント */
@Composable
fun PartialScoreBoard(typeName: String, score: Double, maxScore: Int, backgroundColor: Color) {
    Surface(
        color = backgroundColor,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .height(IntrinsicSize.Max)
            .border(2.dp, backgroundColor.darken(0.8f), RoundedCornerShape(8.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp, 8.dp)
        ) {
            Text(
                text = typeName,
                fontFamily = FontFamily(Font(R.font.koruri_bold, FontWeight.Bold)),
                fontSize = 18.sp
            )
            Text(
                text = String.format(Locale.US, "%.1f", score).plus('/').plus(maxScore),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

/* 別の画面に移動するボタン用コンポーネント */
@Composable
fun BackToOtherScreenButton(
    displayName: String,
    backToOtherScreenClick: () -> Unit
) {
    Surface(
        onClick = backToOtherScreenClick,
        color = Color.LightGray,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, Color.LightGray.darken(0.8f), RoundedCornerShape(8.dp)),
    ) {
        Text(
            text = displayName,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.koruri_bold)),
            modifier = Modifier.padding(24.dp)
        )
    }
}

/* 背景画像 */
@Composable
fun TitleScreenBackgroundImage(titleScreenBackGroundImage: Int = R.drawable.title_screen_background_image) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = titleScreenBackGroundImage),
            contentDescription = "Title Screen Background Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}
