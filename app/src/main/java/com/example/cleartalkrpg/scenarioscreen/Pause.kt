package com.example.cleartalkrpg.scenarioscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen

@Composable
fun PauseMenu(
    onBackToScenarioSelectScreenClick: () -> Unit,
    onRestartClick: () -> Unit,
    onResumeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(400.dp)
            .height(300.dp)
            .background(Color.Gray, shape = RoundedCornerShape(16.dp)) // 背景色と角丸
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onRestartClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(text = "最初からやり直す", color = Color.White, fontSize = 16.sp)
            }
            Button(
                onClick = onBackToScenarioSelectScreenClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "シナリオ選択へ戻る", color = Color.White, fontSize = 16.sp)
            }
            Button(
                onClick = onResumeClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text(text = "再開する", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}
