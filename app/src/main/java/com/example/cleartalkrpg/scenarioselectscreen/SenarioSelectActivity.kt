package com.example.cleartalkrpg.scenarioselectscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGTheme
import androidx.annotation.DrawableRes


class ScenarioSelectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scenarios = listOf(
                Scenario("Scenario 1", "Description for Scenario 1", R.mipmap.scenario1_image, "5 mins", 1500),
                Scenario("Scenario 2", "Description for Scenario 2", R.mipmap.scenario1_image, "7 mins", 2000)
            )
            val selectedScenario = remember { mutableStateOf(scenarios[0]) }

            ClearTalkRPGTheme {
                ScenarioSelectScreen(
                    state = ScenarioSelectState(
                        scenarios = scenarios,
                        selectedScenario = selectedScenario.value,
                        onScenarioSelected = { scenario -> selectedScenario.value = scenario }
                    ),
                    onBackClick = { /* Handle back navigation */ },
                    onStartScenarioClick = {
                        // シナリオを開始する処理をここに記述
                        startScenario(selectedScenario.value)
                    }
                )
            }
        }
    }

    private fun startScenario(scenario: Scenario) {
        // シナリオ開始処理を実装（別のActivityを起動するなど）
    }
}


data class Scenario(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int,
    val timeRequired: String,
    val highScore: Int
)
