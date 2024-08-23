package com.example.cleartalkrpg.scenarioselectscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cleartalkrpg.R

@Composable
fun rememberScenarioSelectState(): ScenarioSelectState {
    val scenarios = listOf(
        Scenario("Scenario 1", "Description for Scenario 1", R.mipmap.scenario1_image, "5 mins", 1500),
        Scenario("Scenario 2", "Description for Scenario 2", R.mipmap.scenario1_image, "7 mins", 2000)
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
