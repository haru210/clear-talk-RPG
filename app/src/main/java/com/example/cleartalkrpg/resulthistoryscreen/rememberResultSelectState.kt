package com.example.cleartalkrpg.resulthistoryscreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cleartalkrpg.database.Result
import com.example.cleartalkrpg.scenarioscreen.waitForTrue
import com.example.cleartalkrpg.viewmodel.ResultViewModel
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.delay

@Composable
fun rememberResultSelectState(
    resultViewModel: ResultViewModel
): ResultSelectState {
    val results by resultViewModel.allResults.observeAsState(mutableListOf())
    val selectedResult = remember {
        mutableStateOf(if(results.isNotEmpty()) results.first() else null)
    }
    LaunchedEffect(results) {
        if (results.isNotEmpty()) {
            selectedResult.value = results.first()
        } else {
            selectedResult.value = null
        }
    }

    return ResultSelectState(
        results = results,
        selectedResult = selectedResult.value,
        onResultSelected = { result -> selectedResult.value = result }
    )
}

data class ResultSelectState(
    val results: List<Result>,
    val selectedResult: Result?,
    val onResultSelected: (Result) -> Unit
)
