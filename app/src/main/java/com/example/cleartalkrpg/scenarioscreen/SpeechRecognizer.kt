package com.example.cleartalkrpg.scenarioscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun SpeechRecognize(context: Context) {
    val speechRecognizerManager = remember { SpeechRecognizerManager(context) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if(isGranted){
                speechRecognizerManager.startListening()
            }
        }
    )
    var target : String = "test"
    var res : String = speechRecognizerManager.speechResult.value
    val tar_length = target.length
    val res_length = res.length
    //編集距離を求める
    var dp = Array(tar_length + 1) { IntArray(res_length + 1) }

    for(i in 0..tar_length) {
        dp[i][0] = i
    }
    for(j in 0..res_length) {
        dp[0][j] = j
    }

    for(i in 1..tar_length) {
        for(j in 1..res_length) {
            val cost = if(target[i - 1] == res[j - 1]) 0 else 1
            dp[i][j] = minOf(
                dp[i-1][j] + 1,
                dp[i][j-1] + 1,
                dp[i-1][j-1] + cost
                )
        }
    }
    val lev_dis = dp[tar_length][res_length]
    val clarity : Int = 40 - lev_dis * 2

}

class SpeechRecognizerManager(private val context: Context) {
    private var speechRecognizer: SpeechRecognizer? = null
    val speechResult = mutableStateOf("")

    fun startListening() {
        if(SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {}
                override fun onError(error: Int) {
                    speechResult.value = "Error: $error"
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    speechResult.value = matches?.joinToString(separator = "\n") ?: "No Results"
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ja-JP")
            }
            speechRecognizer?.startListening(intent)
        } else {
            speechResult.value = "NoData"
        }

    }
    fun stopListening() {
        speechRecognizer?.stopListening()
        speechRecognizer?.destroy()
        speechRecognizer = null
    }
}