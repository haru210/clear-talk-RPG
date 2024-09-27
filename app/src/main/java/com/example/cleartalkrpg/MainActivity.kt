package com.example.cleartalkrpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleartalkrpg.database.RoomApplication
import com.example.cleartalkrpg.resultscreen.ResultScreen
import com.example.cleartalkrpg.scenarioscreen.ScenarioScreen
import com.example.cleartalkrpg.titlescreen.TitleScreen
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGTheme
import com.example.cleartalkrpg.scenarioselectscreen.rememberScenarioSelectState
import com.example.cleartalkrpg.scenarioselectscreen.ScenarioSelectScreen
import com.example.cleartalkrpg.resulthistoryscreen.ResultHistoryScreen
import com.example.cleartalkrpg.database.Result
import com.example.cleartalkrpg.viewmodel.ResultViewModel
import com.example.cleartalkrpg.viewmodel.ResultViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    // private val dao = RoomApplication.database.resultDao()
    // private var resultList = mutableStateListOf<Result>()

    private val resultViewModel: ResultViewModel by viewModels {
        ResultViewModelFactory((application as CTRPGApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClearTalkRPGTheme {
                /*
                postResult(
                    scenarioTitle = "かまきり",
                    totalScore = 88.8f,
                    volumeScore = 28.1f,
                    clarityScore = 38.2f,
                    speedScore = 22.5f,
                    comment = "もう少しゆっくり一言一言大切に話してみましょう！"
                )
                */
                /* 読み込んだデータを各画面で使用できるように渡す */
                // SceneGenerator(resultList)
                SceneGenerator(resultViewModel = resultViewModel)
            }
        }
        /* リザルトリストの読み込み */
        // loadResult()
    }

    /* リザルトリストの読み込み関数 (命名ミスってる loadResult() -> loadResultList()) */
    /*
    private fun loadResult() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.getAll().forEach { result ->
                    resultList.add(result)
                }
            }
        }
    }
    */

    /* リザルトリストへの挿入関数 */
    /*
    private fun postResult(
        scenarioTitle: String,
        totalScore: Float,
        volumeScore: Float,
        clarityScore: Float,
        speedScore: Float,
        comment: String
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.post(Result(
                    scenario_title = scenarioTitle,
                    total_score = totalScore,
                    volume_score = volumeScore,
                    clarity_score = clarityScore,
                    speed_score = speedScore,
                    comment = comment
                ))

                resultList.clear()
                loadResult()
            }
        }
    }
    */

    /* リザルトの削除関数 */
    /*
    private fun deleteResult(result: Result) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                dao.delete(result)

                resultList.clear()
                loadResult()
            }
        }
    }
    */
}

enum class ClearTalkRPGScreen {
    Title,
    SelectScenario,
    Scenario,
    Result,
    ResultHistory
}

@Composable
fun SceneGenerator(resultViewModel: ResultViewModel) {
    val navController = rememberNavController()
    val scenarioSelectState = rememberScenarioSelectState() // ここで定義していないためエラー

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ClearTalkRPGScreen.Title.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ClearTalkRPGScreen.Title.name) {
                TitleScreen(
                    navController = navController,
                    resultViewModel = resultViewModel
                )
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
                    resultViewModel = resultViewModel,
                    navController = navController
                )
            }
        }
    }
}
