package com.example.cleartalkrpg.resulthistoryscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cleartalkrpg.database.Result
import com.example.cleartalkrpg.viewmodel.ResultViewModel

@Composable
fun rememberResultSelectState(
    resultViewModel: ResultViewModel
): ResultSelectState {
    val results by resultViewModel.allResults.observeAsState(emptyList())
    val selectedResult = remember { mutableStateOf(results.first()) }

    return ResultSelectState(
        results = results,
        selectedResult = selectedResult.value,
        onResultSelected = { result -> selectedResult.value = result }
    )
}

data class ResultSelectState(
    val results: List<Result>,
    val selectedResult: Result,
    val onResultSelected: (Result) -> Unit
)
