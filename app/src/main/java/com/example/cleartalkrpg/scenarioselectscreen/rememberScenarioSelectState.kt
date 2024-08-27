package com.example.cleartalkrpg.scenarioselectscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cleartalkrpg.R

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

data class ScenarioSelectState(
    val scenarios: List<Scenario>,
    val selectedScenario: Scenario,
    val onScenarioSelected: (Scenario) -> Unit
)