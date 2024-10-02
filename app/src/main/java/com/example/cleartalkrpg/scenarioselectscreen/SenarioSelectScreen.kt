package com.example.cleartalkrpg.scenarioselectscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
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
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.animateColorAsState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.runtime.*
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ClearTalkRPGScreen
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.Dp
import com.example.cleartalkrpg.database.Scenario

@Composable
fun CustomTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(255, 142, 127, 255))
            .padding(4.dp)
            .clickable { onBackClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "タイトル画面へ",
            color = Color(0, 0, 0),
            fontSize = 13.sp,
            modifier = Modifier.weight(1f, fill = false) // 左寄せ
        )
        Text(
            text = "シナリオ選択",
            color = Color(0, 0, 0),
            fontSize = 13.sp,
            modifier = Modifier.weight(1f, fill = false) // 右寄せ
        )
    }
}

@Composable
fun TitleScreenBackgroundImage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.title_screen_background_image),
            contentDescription = "Title screen background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
fun ScenarioSelectScreen(navController: NavController) {
    val state = rememberScenarioSelectState()
    TitleScreenBackgroundImage()

    // 初期状態で一番上のシナリオが選択されているように selectedScenarioIndex を 0 に設定
    var selectedScenarioIndex by remember { mutableStateOf(0) }
    var isScenarioSelected by remember { mutableStateOf(false) } // シナリオ決定後のフラグ

    // 初期シナリオを選択
    LaunchedEffect(Unit) {
        state.onScenarioSelected(state.scenarios[selectedScenarioIndex])
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            CustomTopBar(onBackClick = { navController.navigate(ClearTalkRPGScreen.Title.name) })
            Row(modifier = Modifier.fillMaxSize()) {
                // シナリオ選択画面
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = state.selectedScenario!!.jacketImage),
                        contentDescription = state.selectedScenario?.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = state.selectedScenario.description,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "所要時間: ${state.selectedScenario.timeRequired}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "最高スコア: ${state.selectedScenario.highScore}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                // シナリオボタン
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    state.scenarios.forEachIndexed { index, scenario ->
                        ScenarioButton(
                            scenario = scenario,
                            isSelected = selectedScenarioIndex == index,
                            onClick = {
                                selectedScenarioIndex = index
                                state.onScenarioSelected(scenario)
                            }
                        )
                    }
                }
            }
        }

        // シナリオ決定ボタン
        if (!isScenarioSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(60.dp)
                    .background(Color(0, 127, 211, 255), shape = CircleShape)
                    .clickable {
                        isScenarioSelected = true // シナリオ決定時にオーバーレイを表示
                    }
                    .shadow(4.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "決定!",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        // オーバーレイとスタートボタン
        if (isScenarioSelected) {
            // オーバーレイ
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { isScenarioSelected = false}
            )

            // アニメーションでスタートボタンをスライドイン
            var offsetX by remember { mutableStateOf(300.dp) }
            LaunchedEffect(Unit) {
                offsetX = 0.dp // アニメーションでスライドイン
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
                    .width(200.dp)
                    .offset(x = animateDpAsState(targetValue = offsetX, animationSpec = tween(500)).value) // アニメーション
                    .size(80.dp)
                    .background(Color.Red, shape = RoundedCornerShape(30.dp))
                    .clickable {
                        navController.navigate(ClearTalkRPGScreen.Scenario.name) // シナリオ画面へ遷移
                    }
                    .border(2.dp, Color.White, shape = RoundedCornerShape(30.dp))
                    .shadow(4.dp, shape = RoundedCornerShape(30.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "スタート",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}


@Composable
fun ScenarioButton(scenario: Scenario, isSelected: Boolean, onClick: () -> Unit) {
    val offset by animateDpAsState(targetValue = if (isSelected) -100.dp else 0.dp)
    val backgroundColor by animateColorAsState(targetValue = if (isSelected) Color(
        236,
        36,
        83,
        255
    ) else Color(119, 124, 128, 255)
    )

    Box(
        modifier = Modifier
            .width(290.dp)
            .height(65.dp)
            .padding(8.dp)
            .background(Color.Transparent)
            .offset(x = offset)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor, shape = RoundedCornerShape(8.dp))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = scenario.title,
                fontSize = 25.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}