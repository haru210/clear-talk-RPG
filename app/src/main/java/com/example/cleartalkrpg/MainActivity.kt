package com.example.cleartalkrpg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.cleartalkrpg.titlescreen.TitleScreen
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


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
    Result
}
@Composable
fun SceneGenerator() {
    val navController = rememberNavController()
    Scaffold(

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ClearTalkRPGScreen.Title.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ClearTalkRPGScreen.SelectScenario.name) {

            }
            composable(route = ClearTalkRPGScreen.Scenario.name) {

            }
            composable(route = ClearTalkRPGScreen.Scenario.name) {

            }
            composable(route = ClearTalkRPGScreen.Result.name) {

            }
        }
    }
}
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Good morning $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ClearTalkRPGTheme {
//        Greeting("test")
//    }
//}