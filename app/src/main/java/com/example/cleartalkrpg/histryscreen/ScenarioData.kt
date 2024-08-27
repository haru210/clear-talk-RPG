package com.example.cleartalkrpg.histryscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.scenarioselectscreen.Scenario
import com.example.cleartalkrpg.scenarioselectscreen.ScenarioSelectState
import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf

val scenarioHistory = mutableStateListOf<Scenario>()

@Composable
fun rememberScenarioSelectState(): ScenarioSelectState {
    val scenarios = listOf(
        Scenario(
            title = "Scenario 1",
            description = "Description for Scenario 1",
            imageRes = R.mipmap.scenario1_image,
            timeRequired = "5 mins",
            highScore = 1500,
            playDate = "2024-08-21",
            totalScore = "1000",
            volume = "80",
            clarity = "70",
            speed = "90",
            comment = "Good work!"
        ),
        Scenario(
            title = "Scenario 2",
            description = "Description for Scenario 2",
            imageRes = R.mipmap.scenario1_image,
            timeRequired = "7 mins",
            highScore = 2000,
            playDate = "2024-08-22",
            totalScore = "1500",
            volume = "85",
            clarity = "75",
            speed = "95",
            comment = "Excellent performance!"
        )
    )
    val selectedScenario = remember { mutableStateOf(scenarios[0]) }

    return ScenarioSelectState(
        scenarios = scenarios,
        selectedScenario = selectedScenario.value,
        onScenarioSelected = { scenario -> selectedScenario.value = scenario }
    )
}

fun addScenarioHistory(
    scenario: Scenario,
    playDate: String,
    totalScore: String,
    volume: String,
    clarity: String,
    speed: String,
    comment: String
) {
    val newScenario = scenario.copy(
        playDate = playDate,
        totalScore = totalScore,
        volume = volume,
        clarity = clarity,
        speed = speed,
        comment = comment
    )

    // 記録をリストに追加
    scenarioHistory.add(newScenario) // scenarioHistory もしくはそれに相当するリストを用意する必要があります

    // リストが一週間以上の記録を保持している場合、古いものを削除
    if (scenarioHistory.size > 7) {
        scenarioHistory.removeAt(0)
    }
}
