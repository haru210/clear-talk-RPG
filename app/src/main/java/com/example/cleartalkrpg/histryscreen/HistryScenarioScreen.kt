package com.example.cleartalkrpg.histryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.scenarioselectscreen.Scenario
import com.example.cleartalkrpg.scenarioselectscreen.ScenarioSelectState

@Composable
fun rememberScenarioHistoryState(): List<Scenario> {
    val scenarioHistory = remember { mutableStateListOf<Scenario>() }
    return scenarioHistory
}

@Composable
fun CustomTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(4.dp)
            .clickable { onBackClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "戻る",
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically) // 中央に寄せる
        )
        Spacer(modifier = Modifier.weight(1f)) // 空白を作ってシナリオ選択を右端に寄せる
        Text(
            text = "履歴確認",
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically) // 中央に寄せる
        )
    }
}

@Composable
fun HistryScenarioScreen(
    state: ScenarioSelectState,
    onBackClick: () -> Unit,
    navController: NavController
) {
    val scenarioHistory = rememberScenarioHistoryState()

    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                state.selectedScenario?.let { scenario ->
                    // 合計スコアを表示
                    TotalScoreCard(totalScore = scenario.totalScore)
                    Spacer(modifier = Modifier.height(8.dp))

                    // 速さ、明瞭さ、大きさを横に並べる
                    Row(modifier = Modifier.fillMaxWidth()) {
                        ScenarioHistoryDetail(label = "速さ", value = scenario.speed, modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(8.dp))
                        ScenarioHistoryDetail(label = "明瞭さ", value = scenario.clarity, modifier = Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(8.dp))
                        ScenarioHistoryDetail(label = "音量", value = scenario.volume, modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    CommentCard(comment = scenario.comment) // 一言コメント
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
                    ScenarioHistoryButton(
                        title = scenario.title,
                        date = "プレイ日: ${scenario.playDate}",
                        onClick = { state.onScenarioSelected(scenario) }
                    )
                }
            }
        }
    }
}

@Composable
fun TotalScoreCard(totalScore: String) {
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
                text = "合計スコア",
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = totalScore,
                fontSize = 24.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ScenarioHistoryDetail(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
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
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ScenarioHistoryButton(title: String, date: String, onClick: () -> Unit) {
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
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = date,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
