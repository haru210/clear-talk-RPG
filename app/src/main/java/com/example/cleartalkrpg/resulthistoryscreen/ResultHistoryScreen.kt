package com.example.cleartalkrpg.resulthistoryscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.utils.formatDateToDateString
import com.example.cleartalkrpg.utils.formatDateToTimeString
import com.example.cleartalkrpg.R

@Composable
fun ResultHistoryScreen(
    state: ResultSelectState,
    onBackClick: () -> Unit,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        /* ヘッダー */
        CustomTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.weight(1f)) {
            Box(modifier = Modifier.weight(1f)) {
                /* 選択中のリザルトの表示 */
                ResultCard(state = state)
            }
            Box(modifier = Modifier.weight(1f)) {
                /* リザルト履歴の一覧表示 */
                ResultHistoryList(state = state)
            }
        }
    }
}

@Composable
fun ResultCard(
    state: ResultSelectState
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        state.selectedResult?.let { result ->
            /* 合計スコアを表示 */
            TotalScoreCard(totalScore = result.total_score.toString())

            Spacer(modifier = Modifier.height(8.dp))

            /* 各スコアカードの表示 */
            Row(modifier = Modifier.fillMaxWidth()) {
                /* 速さのスコアカード */
                ScenarioHistoryDetail(
                    label = "速さ",
                    value = result.speed_score.toString(),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                /* 明瞭さのスコアカード */
                ScenarioHistoryDetail(
                    label = "明瞭さ",
                    value = result.clarity_score.toString(),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                /* 音量のスコアカード */
                ScenarioHistoryDetail(
                    label = "音量",
                    value = result.volume_score.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            /* 一言コメントの表示 */
            CommentCard(comment = result.comment)
        }
    }
}

@Composable
fun ResultHistoryList(
    state: ResultSelectState
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        state.results.forEach { result ->
            val playDate: String = formatDateToDateString(result.created_at)
            val playTime: String = formatDateToTimeString(result.created_at)
            ScenarioHistoryButton(
                title = result.scenario_title,
                date = "プレイ日時: ${playDate} ${playTime}",
                onClick = { state.onResultSelected(result) }
            )
        }
    }
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
            fontFamily = FontFamily(Font(R.font.koruri_regular)),
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically) // 中央に寄せる
        )
        Spacer(modifier = Modifier.weight(1f)) // 空白を作ってシナリオ選択を右端に寄せる
        Text(
            text = "履歴確認",
            fontFamily = FontFamily(Font(R.font.koruri_regular)),
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically) // 中央に寄せる
        )
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
                fontFamily = FontFamily(Font(R.font.koruri_bold)),
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
                fontFamily = FontFamily(Font(R.font.koruri_bold)),
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
                fontFamily = FontFamily(Font(R.font.koruri_bold)),
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment,
                fontFamily = FontFamily(Font(R.font.koruri_bold)),
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
                fontFamily = FontFamily(Font(R.font.koruri_regular)),
                fontSize = 14.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = date,
                fontFamily = FontFamily(Font(R.font.koruri_regular)),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
