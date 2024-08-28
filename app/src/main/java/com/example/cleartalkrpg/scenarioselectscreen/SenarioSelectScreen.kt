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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import com.example.cleartalkrpg.ClearTalkRPGScreen

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
    )  {
        Text(
            text = "タイトル画面へ",
            color = Color(0,0,0),
            fontSize = 13.sp,
            modifier = Modifier.weight(1f, fill = false) // 左寄せ
        )
        Text(
            text = "シナリオ選択",
            color = Color(0,0,0),
            fontSize = 13.sp,
            modifier = Modifier.weight(1f, fill = false) // 右寄せ
        )
    }
}

@Composable
fun ScenarioSelectScreen(
    navController: NavController
) {
    val state = rememberScenarioSelectState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomTopBar(onBackClick = { navController.navigate(ClearTalkRPGScreen.Title.name) })
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

                    // 描画用の Box を使用して枠組みを丸くする
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(8.dp)) // 影を外側に追加
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)) // 枠組みを丸くする
                    ) {
                        Text(
                            text = state.selectedScenario.description,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(8.dp)) // 影を外側に追加
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp)) // 枠組みを丸くする
                        ) {
                            Text(
                                text = "所要時間: ${state.selectedScenario.timeRequired}",
                                fontSize = 14.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(8.dp)) // 影を外側に追加
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp)) // 枠組みを丸くする
                        ) {
                            Text(
                                text = "最高スコア: ${state.selectedScenario.highScore}",
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
                .clickable(onClick = { navController.navigate(ClearTalkRPGScreen.Scenario.name) })
                .border(2.dp, Color.White, CircleShape) // ボタンの枠線
                .shadow(4.dp, CircleShape), // シャドウを追加
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
    val highScore: Int,
    val totalScore: String,  // 追加
    val clarity: String,      // 追加
    val speed: String,        // 追加
    val volume: String,       // 追加
    val comment: String,      // 追加
    val playDate: String      // 追加
)