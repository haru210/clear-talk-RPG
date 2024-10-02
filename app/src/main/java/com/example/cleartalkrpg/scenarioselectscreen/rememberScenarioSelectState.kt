package com.example.cleartalkrpg.scenarioselectscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cleartalkrpg.database.Scenario
import com.example.cleartalkrpg.viewmodel.ScenarioViewModel

@Composable
fun rememberScenarioSelectState(
    scenarioViewModel: ScenarioViewModel
): ScenarioSelectState {
    val scenarios by scenarioViewModel.allScenarios.observeAsState(mutableListOf())
    val selectedScenario = remember {
        mutableStateOf(if(scenarios.isNotEmpty()) scenarios.first() else null)
    }
    LaunchedEffect(scenarios) {
        if (scenarios.isNotEmpty()) {
            selectedScenario.value = scenarios.first()
        } else {
            selectedScenario.value = null
        }
    }

    return ScenarioSelectState(
        scenarios = scenarios,
        selectedScenario = selectedScenario.value,
        onScenarioSelected = { scenario -> selectedScenario.value = scenario }
    )
}

data class ScenarioSelectState(
    val scenarios: List<Scenario>,
    val selectedScenario: Scenario?,
    val onScenarioSelected: (Scenario) -> Unit
)