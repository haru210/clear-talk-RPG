package com.example.cleartalkrpg.resultscreen

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen

@Composable
fun ResultScreen(navController: NavController) {
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
                TotalScoreBoard(totalScore = 88.8, backgroundColor = Color.Cyan)
                Surface(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    CommentBoard(comment = "もう少しゆっくり一言一言大切に話してみましょう！")
                }
            }
            Text(
                text = "詳細評価",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                PartialScoreBoard(typeName = "音量", score = 28.1, maxScore = 30, backgroundColor = Color.Red)
                PartialScoreBoard(typeName = "明瞭さ", score = 38.2, maxScore = 40, backgroundColor = Color.Blue)
                PartialScoreBoard(typeName = "速さ", score = 22.5, maxScore = 30, backgroundColor = Color.Green)
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

@Composable
fun TotalScoreBoard(totalScore: Double, backgroundColor: Color) {
    Surface(
        color = backgroundColor,
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = totalScore.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                text = "点",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
    }
}

@Composable
fun CommentBoard(comment: String) {
    Surface(
        color = Color.LightGray.copy(alpha = 0.60f),
        modifier = Modifier.clip(RoundedCornerShape(8.dp))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(12.dp, 8.dp)
            ) {
                Text(text = "【一言コメント】")
                Text(text = comment)
            }
        }
    }
}

@Composable
fun PartialScoreBoard(typeName: String, score: Double, maxScore: Int, backgroundColor: Color) {
    Surface(
        color = backgroundColor,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .height(IntrinsicSize.Max)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp, 8.dp)
        ) {
            Text(
                text = typeName
            )
            Text(
                text = score.toString().plus('/').plus(maxScore),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun BackToOtherScreenButton(
    displayName: String,
    backToOtherScreenClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        onClick = backToOtherScreenClick,
        color = Color.LightGray,
    ) {
        Text(
            text = displayName,
            modifier = Modifier.padding(24.dp)
        )
    }
}
