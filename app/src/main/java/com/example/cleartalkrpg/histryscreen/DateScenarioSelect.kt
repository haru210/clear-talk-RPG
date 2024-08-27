/*package com.example.cleartalkrpg.histryscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleartalkrpg.scenarioselectscreen.ScenarioSelectState

@Composable
fun TopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray) // 背景色を設定
            .padding(7.dp)
            .height(16.5.dp)
            .clickable { onBackClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "戻る",
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f, fill = false) // 左寄せ
        )
        Text(
            text = "シナリオ選択",
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier.weight(1f, fill = false) // 右寄せ
        )
    }
}

@Composable
fun ScenarioSelectionScreen(state: ScenarioSelectState, onBackClick: () -> Unit, onStartScenarioClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            // 左側のカラム - スコア表示
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // スコアカード
                ScoreCard(label = "合計点", value = state.selectedScenario.totalScore)
                ScoreCard(label = "音量", value = state.selectedScenario.volume)
                ScoreCard(label = "明瞭さ", value = state.selectedScenario.clarity)
                ScoreCard(label = "速さ", value = state.selectedScenario.speed)
                Spacer(modifier = Modifier.height(16.dp)) // スペース
                CommentCard(comment = state.selectedScenario.comment) // 一言コメント
                Spacer(modifier = Modifier.height(16.dp)) // ボタンの上にスペースを追加
                Button(
                    onClick = onStartScenarioClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "スタート")
                }
            }

            // 右側のカラム - シナリオリスト
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                state.scenarios.forEach { scenario ->
                    ScenarioItemButton(
                        title = scenario.title,
                        date = "プレイ日: ${scenario.playDate}", // 日付を表示する
                        onClick = { state.onScenarioSelected(scenario) }
                    )
                }
            }
        }

        // 丸型のスタートボタンを右下に配置
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(60.dp) // ボタンのサイズを指定
                .background(Color.Blue, shape = CircleShape) // 丸型の背景色
                .clickable(onClick = onStartScenarioClick)
                .border(2.dp, Color.White, CircleShape), // ボタンの枠線
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "スタート",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ScoreCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp)) // ラベルと値の間にスペースを追加
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun CommentCard(comment: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "コメント",
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp)) // ラベルとコメントの間にスペースを追加
            Text(
                text = comment,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ScenarioItemButton(title: String, date: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp)) // タイトルと日付の間にスペースを追加
            Text(
                text = date,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
*/