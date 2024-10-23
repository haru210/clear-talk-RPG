package com.example.cleartalkrpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleartalkrpg.charactersheetscreen.CharacterSheetScreen
import com.example.cleartalkrpg.charactersheetscreen.rememberCharacterSheetSelectState
import com.example.cleartalkrpg.database.CharacterSheet
import com.example.cleartalkrpg.resultscreen.ResultScreen
import com.example.cleartalkrpg.scenarioscreen.ScenarioScreen
import com.example.cleartalkrpg.titlescreen.TitleScreen
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGTheme
import com.example.cleartalkrpg.scenarioselectscreen.rememberScenarioSelectState
import com.example.cleartalkrpg.scenarioselectscreen.ScenarioSelectScreen
import com.example.cleartalkrpg.resulthistoryscreen.ResultHistoryScreen
import com.example.cleartalkrpg.resulthistoryscreen.rememberResultSelectState
import com.example.cleartalkrpg.viewmodel.ResultViewModel
import com.example.cleartalkrpg.viewmodel.ResultViewModelFactory
import com.example.cleartalkrpg.viewmodel.ScenarioViewModel
import com.example.cleartalkrpg.viewmodel.ScenarioViewModelFactory
import com.example.cleartalkrpg.database.Result
import com.example.cleartalkrpg.database.Scenario
import com.example.cleartalkrpg.homescreen.HomeScreen
import com.example.cleartalkrpg.loadingscreen.LoadingScreen
import com.example.cleartalkrpg.viewmodel.CharacterSheetViewModel
import com.example.cleartalkrpg.viewmodel.CharacterSheetViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val resultViewModel: ResultViewModel by viewModels {
        ResultViewModelFactory((application as CTRPGApplication).repository)
    }
    private val scenarioViewModel: ScenarioViewModel by viewModels {
        ScenarioViewModelFactory((application as CTRPGApplication).repository)
    }
    private val characterSheetViewModel: CharacterSheetViewModel by viewModels {
        CharacterSheetViewModelFactory((application as CTRPGApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClearTalkRPGTheme {
                SceneGenerator(
                    resultViewModel = resultViewModel,
                    scenarioViewModel = scenarioViewModel,
                    characterSheetViewModel = characterSheetViewModel
                )
            }
        }
    }
}

enum class ClearTalkRPGScreen {
    Title,
    Home,
    CreateCharacterSheet,
    SelectCharacter,
    SelectScenario,
    Scenario,
    Result,
    ResultHistory,
    Loading
}

@Composable
fun SceneGenerator(
    resultViewModel: ResultViewModel,
    scenarioViewModel: ScenarioViewModel,
    characterSheetViewModel: CharacterSheetViewModel
) {
    val navController = rememberNavController()
    val resultSelectState = rememberResultSelectState(resultViewModel = resultViewModel)
    val scenarioSelectState = rememberScenarioSelectState(scenarioViewModel = scenarioViewModel)
    val characterSheetSelectState = rememberCharacterSheetSelectState(characterSheetViewModel = characterSheetViewModel)

    val resultState = remember { mutableStateOf<Result?>(null) }
    val scenarioUpdateState = remember { mutableStateOf<Scenario?>(null) }
    val resultScoresState = remember { mutableStateOf<Map<String, Double>>(emptyMap()) }
    val resultCommentState = remember { mutableStateOf("") }
    val navigationState = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(resultState.value) {
        resultState.value?.let { result ->
            withContext(Dispatchers.IO) {
                resultViewModel.post(result)
            }
            resultState.value = null
        }
    }

    LaunchedEffect(scenarioUpdateState.value) {
        scenarioUpdateState.value?.let { scenario ->
            withContext(Dispatchers.IO) {
                scenarioViewModel.update(scenario)
            }
            scenarioUpdateState.value = null
        }
    }

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ClearTalkRPGScreen.Title.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ClearTalkRPGScreen.Title.name) {
                TitleScreen(navController = navController)
            }
            composable(route = ClearTalkRPGScreen.Home.name) {
                characterSheetSelectState.selectedCharacter?.let { it1 -> HomeScreen(navController = navController, selectedCharacterSheet = it1) }
            }
            composable(route = ClearTalkRPGScreen.CreateCharacterSheet.name) {
                CharacterSheetScreen(
                    navController = navController,
                    characterSheetViewModel = characterSheetViewModel
                )
            }
            composable(route = ClearTalkRPGScreen.SelectCharacter.name) {
                CharacterSheetScreen(
                    navController = navController,
                    characterSheetViewModel = characterSheetViewModel
                )
            }
            composable(route = ClearTalkRPGScreen.SelectScenario.name) {
                ScenarioSelectScreen(
                    navController = navController,
                    scenarioSelectState = scenarioSelectState,
                    navigationState = navigationState
                )
            }
            composable(route = ClearTalkRPGScreen.Loading.name) {
                LoadingScreen(navigation = { navController.navigate(navigationState.value!!) })
            }
            composable(route = ClearTalkRPGScreen.Scenario.name) {
                ScenarioScreen(
                    navController = navController,
                    scenarioViewModel = scenarioViewModel,
                    resultViewModel = resultViewModel,
                    resultState = resultState,
                    scenarioUpdateState = scenarioUpdateState,
                    /* PrimaryKeyのautoGenerateプロパティの仕様上idが1から始まるので、
                    * リスト等の添字に使用する場合は-1する必要がある。
                    * もともとシナリオのidを添字に使用する設計に問題があるので修正要検討。 */
                    selectedScenarioId = scenarioSelectState.selectedScenario!!.id - 1, // 技術的負債
                    resultScoresState = resultScoresState,
                    resultCommentState = resultCommentState
                )
            }
            composable(route = ClearTalkRPGScreen.Result.name) {
                ResultScreen(
                    navController = navController,
                    scores = resultScoresState.value,
                    comment = resultCommentState.value
                )
            }
            composable(route = ClearTalkRPGScreen.ResultHistory.name) {
                ResultHistoryScreen(
                    state = resultSelectState,
                    onBackClick = { navController.popBackStack() }, // 戻るボタンの処理を渡す
                    navController = navController
                )
            }
        }
    }
}
