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
import androidx.navigation.NavController
import androidx.annotation.DrawableRes

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
fun ScenarioSelectScreen(
    state: ScenarioSelectState,
    onBackClick: () -> Unit,
    onStartScenarioClick: () -> Unit, // 追加
    navController: NavController // 追加
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomTopBar(onBackClick = onBackClick)
            Row(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
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
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
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
                            elevation = CardDefaults.elevatedCardElevation(4.dp)
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
                            elevation = CardDefaults.elevatedCardElevation(4.dp)
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
fun ScenarioButton(scenario: Scenario, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(60.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = scenario.title, fontSize = 14.sp, color = Color.Black)
    }
}

data class Scenario(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int,
    val timeRequired: String,
    val highScore: Int
)
