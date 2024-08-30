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
    var targetCnt : Int = 4
    var res : String = speechRecognizerManager.speechResult.value
    val targetLength = target.length
    val resLength = res.length

    //編集距離を求める
    var dp = Array(targetLength + 1) { IntArray(resLength + 1) }

    for(i in 0..targetLength) {
        dp[i][0] = i
    }
    for(j in 0..resLength) {
        dp[0][j] = j
    }

    for(i in 1..targetLength) {
        for(j in 1..resLength) {
            val cost = if(target[i - 1] == res[j - 1]) 0 else 1
            dp[i][j] = minOf(
                dp[i-1][j] + 1,
                dp[i][j-1] + 1,
                dp[i-1][j-1] + cost
                )
        }
    }
    val levenDis = dp[targetLength][resLength]
    //明瞭さの点数を求める
    val clarityScore : Int = 40 - levenDis * 2

    //速さの点数を求める
    val speed : Double = targetCnt.toDouble() / (speechRecognizerManager.speechDuration.toDouble() / 1000.0F)
    //満点となる速度の最大値と最小値
    val speedMin = 5.5F
    val speedMax = 7.5F

    var speedScore = 30
    if(speed < speedMin){
        speedScore -= Math.round(speedMin - speed).toInt()
    }
    else if(speed > speedMax){
        speedScore -= Math.round(speed - speedMax).toInt()
    }
    var volumeAvg : Double = 0.0
    //音量の点数を求める
    for(i in 0..speechRecognizerManager.volumeList.size - 1){
        volumeAvg += speechRecognizerManager.volumeList[i].second.toDouble() * ((speechRecognizerManager.volumeList[i].first.toDouble() / speechRecognizerManager.speechDuration.toDouble() ) / 1000.0)
    }
    val volumeScore = Math.round(volumeAvg * 3.0)
}

class SpeechRecognizerManager(private val context: Context) {
    private var speechRecognizer: SpeechRecognizer? = null
    val speechResult = mutableStateOf("")

    var startTime : Long = 0
    var endTime : Long = 0
    var speechDuration : Long = 0
    var beforeTime : Long = 0
    val volumeList = ArrayList<Pair<Long, Float>>()

    fun startListening() {
        if(SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {
                    startTime = System.currentTimeMillis()
                    beforeTime = startTime
                }
                override fun onRmsChanged(rmsdB: Float) {
                    volumeList.add(Pair(beforeTime - System.currentTimeMillis(), rmsdB))
                    beforeTime = System.currentTimeMillis()
                }
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {
                    endTime = System.currentTimeMillis()
                    speechDuration = endTime - startTime
                }
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