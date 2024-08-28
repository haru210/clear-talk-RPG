package com.example.cleartalkrpg.historyscreen

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
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.scenarioselectscreen.Scenario
import com.example.cleartalkrpg.scenarioselectscreen.ScenarioSelectState
import com.example.cleartalkrpg.scenarioselectscreen.rememberScenarioSelectState
import androidx.compose.ui.text.font.FontWeight

@Composable
fun rememberScenarioHistoryState(): List<Scenario> {
    val scenarioHistory = remember { mutableStateListOf<Scenario>() }
    return scenarioHistory
}

@Composable
fun ScenarioHistoryDetail(label: String, value: String, maxValue: String, modifier: Modifier = Modifier,cardColor: Color) {
    Card(
        modifier = modifier
            .padding(0.dp)
            .width(50.dp), // 横幅を適切に設定
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)

    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(), // 横幅を親コンテナに合わせる
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .weight(1f) // 左側に配置するため
            )
            Text(
                text = "$value 点 / $maxValue 点",
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun CustomTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(182, 135, 112, 255))
            .padding(4.dp)
            .clickable { onBackClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "タイトル画面へ",
            color = Color(24, 24, 24, 255),
            fontSize = 13.sp,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically) // 中央に寄せる
        )
        Spacer(modifier = Modifier.weight(1f)) // 空白を作ってシナリオ選択を右端に寄せる
        Text(
            text = "履歴確認",
            color = Color(0,0,0),
            fontSize = 13.sp,
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically) // 中央に寄せる
        )
    }
}

@Composable
fun HistoryScenarioScreen(
    navController: NavController
) {
    val scenarioSelectState = rememberScenarioSelectState()
    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopBar(onBackClick = { navController.navigate(ClearTalkRPGScreen.Title.name) })

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.weight(1f)) {
            // 左側のカラム - シナリオ詳細
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.Center // 中央に配置
            ) {
                scenarioSelectState.selectedScenario?.let { scenario ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(0.2f)) // 左側のスペースを追加

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f) // 左側の中央に配置
                        ) {
                            // 合計スコアを表示
                            TotalScoreCard(totalScore = scenario.totalScore)
                            Spacer(modifier = Modifier.height(8.dp))

                            // 速さ、明瞭さ、大きさを縦に並べる
                            Column(modifier = Modifier.fillMaxWidth()) {
                                ScenarioHistoryDetail(
                                    label = "音量",
                                    value = scenario.volume,
                                    maxValue = "100", // 最大値は必要に応じて設定
                                    modifier = Modifier.fillMaxWidth(),
                                    cardColor=Color(244, 67, 54, 255)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                ScenarioHistoryDetail(
                                    label = "明瞭さ",
                                    value = scenario.clarity,
                                    maxValue = "100", // 最大値は必要に応じて設定
                                    modifier = Modifier.fillMaxWidth() ,
                                    cardColor=Color(71, 49, 168, 255)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                ScenarioHistoryDetail(
                                    label = "速さ",
                                    value = scenario.speed,
                                    maxValue = "100", // 最大値は必要に応じて設定
                                    modifier = Modifier.fillMaxWidth(),
                                    cardColor=Color(95, 253, 101, 255)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            CommentCard(comment = scenario.comment) // 一言コメント
                        }

                        Spacer(modifier = Modifier.weight(0.2f)) // 右側のスペースを追加
                    }
                }
            }

            // 右側のカラム - シナリオリスト
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                scenarioSelectState.scenarios.forEach { scenario ->
                    ScenarioHistoryButton(
                        title = scenario.title,
                        date = "プレイ日: ${scenario.playDate}",
                        onClick = { scenarioSelectState.onScenarioSelected(scenario) }
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
        elevation = CardDefaults.elevatedCardElevation(4.dp),
                colors = CardDefaults.cardColors(
                containerColor = Color(81, 235, 255, 255) // ラベンダー色の例
                )
    ) {
        Column(
            modifier = Modifier.padding(0.dp),
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