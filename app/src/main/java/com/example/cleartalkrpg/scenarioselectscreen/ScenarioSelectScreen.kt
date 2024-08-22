package com.example.cleartalkrpg.scenarioselectscreen

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTopBar(onBackClick: () -> Unit) {
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
fun ScenarioSelectScreen(state: ScenarioSelectState, onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        CustomTopBar(onBackClick = onBackClick)
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp) // 追加のパディング
            ) {
                Image(
                    painter = painterResource(id = state.selectedScenario.imageRes),
                    contentDescription = state.selectedScenario.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Crop
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.elevatedCardElevation(4.dp) // 修正箇所
                ) {
                    Text(
                        text = state.selectedScenario.description,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        elevation = CardDefaults.elevatedCardElevation(4.dp) // 修正箇所
                    ) {
                        Text(
                            text = "Time: ${state.selectedScenario.timeRequired}",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        elevation = CardDefaults.elevatedCardElevation(4.dp) // 修正箇所
                    ) {
                        Text(
                            text = "High Score: ${state.selectedScenario.highScore}",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                state.scenarios.forEach { scenario ->
                    ScenarioButton(scenario = scenario, onClick = { state.onScenarioSelected(scenario) })
                }
            }
        }
    }
}

@Composable
fun ScenarioButton(scenario: Scenario, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(100.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = scenario.title, fontSize = 14.sp, color = Color.Black)
    }
}
