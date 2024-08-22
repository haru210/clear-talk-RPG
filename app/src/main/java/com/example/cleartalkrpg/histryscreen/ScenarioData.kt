/*package com.example.cleartalkrpg.histryscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cleartalkrpg.R

@Composable
fun rememberScenarioSelectState(): ScenarioSelectState {
    val scenarios = listOf(
        Scenario("Scenario 1", "Description for Scenario 1", R.mipmap.scenario1_image, "5 mins", 1500, "2024-08-21", "1000", "80", "70", "90", "Good work!"),
        Scenario("Scenario 2", "Description for Scenario 2", R.mipmap.scenario1_image, "7 mins", 2000, "2024-08-22", "1500", "85", "75", "95", "Excellent performance!")
    )
    val selectedScenario = remember { mutableStateOf(scenarios[0]) }

    return ScenarioSelectState(
        scenarios = scenarios,
        selectedScenario = selectedScenario.value,
        onScenarioSelected = { scenario -> selectedScenario.value = scenario }
    )
}

data class Scenario(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int,
    val timeRequired: String,
    val highScore: Int,
    val playDate: String,
    val totalScore: String,
    val volume: String,
    val clarity: String,
    val speed: String,
    val comment: String
)

data class ScenarioSelectState(
    val scenarios: List<Scenario>,
    val selectedScenario: Scenario,
    val onScenarioSelected: (Scenario) -> Unit
)
*/
