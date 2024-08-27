package com.example.cleartalkrpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleartalkrpg.resultscreen.ResultScreen
import com.example.cleartalkrpg.scenarioscreen.ScenarioScreen
import com.example.cleartalkrpg.titlescreen.TitleScreen
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGTheme
import com.example.cleartalkrpg.scenarioselectscreen.rememberScenarioSelectState
import com.example.cleartalkrpg.scenarioselectscreen.ScenarioSelectScreen
import com.example.cleartalkrpg.resulthistoryscreen.ResultHistoryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClearTalkRPGTheme {
                SceneGenerator()
            }
        }
    }
}

enum class ClearTalkRPGScreen {
    Title,
    SelectScenario,
    Scenario,
    Result,
    ResultHistory
}

@Composable
fun SceneGenerator() {
    val navController = rememberNavController()
    val scenarioSelectState = rememberScenarioSelectState() // ここで定義していないためエラー

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ClearTalkRPGScreen.Title.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ClearTalkRPGScreen.Title.name) {
                TitleScreen(navController = navController)
            }
            composable(route = ClearTalkRPGScreen.SelectScenario.name) {
                val state = rememberScenarioSelectState()
                ScenarioSelectScreen(
                    state = state,
                    onBackClick = { navController.popBackStack() },
                    onStartScenarioClick = {
                        navController.navigate(ClearTalkRPGScreen.Scenario.name)
                    },
                    navController = navController
                )
            }
            composable(route = ClearTalkRPGScreen.Scenario.name) {
                ScenarioScreen(navController = navController, selectedScenarioId = 0)
            }
            composable(route = ClearTalkRPGScreen.Result.name) {
                ResultScreen(navController = navController)
            }
            composable(route = ClearTalkRPGScreen.ResultHistory.name) {
                ResultHistoryScreen(
                    state = scenarioSelectState, // 状態を渡す
                    onBackClick = { navController.popBackStack() }, // 戻るボタンの処理を渡す
                    navController = navController
                )
            }
        }
    }
}
