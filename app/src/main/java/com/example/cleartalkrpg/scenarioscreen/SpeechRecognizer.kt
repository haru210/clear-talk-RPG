package com.example.cleartalkrpg.scenarioscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import com.example.cleartalkrpg.database.Screen


class SpeechRecognizerManager(private val context: Context, private val screen: Screen) {
    private var speechRecognizer: SpeechRecognizer? = null
    val speechResult = mutableStateOf("")

    var startTime : Long = 0
    var endTime : Long = 0
    var speechDuration : Long = 0
    val volumeList = ArrayList<Float>()
    var speedScore : Int = 30
    var clarityScore : Int = 40
    var volumeScore : Int = 0
    var score : Triple<Int, Int, Int> = Triple(0, 0, 0)

    private var resultListener:  ((Triple<Int, Int, Int>) -> Unit)? = null

    fun setOnResultListener(listener: (Triple<Int, Int, Int>) -> Unit) {
        resultListener = listener
    }
    fun startListening() {
        if(SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {
                    startTime = System.currentTimeMillis()
                }
                override fun onRmsChanged(rmsdB: Float) {
                    if(rmsdB > 1.0){
                        volumeList.add(rmsdB)
                    }
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
                    score = calcScore(screen)
                    resultListener?.invoke(score)
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

    fun calcScore(screen: Screen) : Triple<Int, Int, Int>{
        var target: String = screen.line
        var targetCnt: Int = screen.lineLength
        var res: String = speechResult.value
        val targetLength = target.length
        val resLength = res.length
        speechDuration -= 400

        //編集距離を求める
        var dp = Array(targetLength + 1) { IntArray(resLength + 1) }

        for (i in 0..targetLength) {
            dp[i][0] = i
        }
        for (j in 0..resLength) {
            dp[0][j] = j
        }

        for (i in 1..targetLength) {
            for (j in 1..resLength) {
                val cost = if (target[i - 1] == res[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + cost
                )
            }
        }
        val levenDis = dp[targetLength][resLength]
        //明瞭さの点数を求める
        clarityScore -= levenDis * 2

        //速さの点数を求める
        val speed: Double =
            targetCnt.toDouble() / (speechDuration.toDouble() / 1000.0F)
        //満点となる速度の最大値と最小値
        val speedMin = 5.5F
        val speedMax = 7.5F

        if (speed < speedMin) {
            speedScore -= Math.round((speedMin - speed) / 1.5).toInt()
        } else if (speed > speedMax) {
            speedScore -= Math.round((speed - speedMax) / 1.5).toInt()
        }
        if(speedScore < 0) speedScore = 0
        var volumeAvg: Double = 0.0
        //音量の点数を求める
        for (i in 0..volumeList.size - 1) {
            volumeAvg += volumeList[i].toDouble()
        }
        volumeAvg /= volumeList.size
        volumeAvg *= 3
        if(volumeAvg > 30.0) volumeAvg = 30.0
        volumeScore = Math.round(volumeAvg).toInt()
        val result = speechResult.value
        return Triple(speedScore, clarityScore, volumeScore)
    }
}

suspend fun waitForTrue(checkCondition: () -> Boolean) {
    while(!checkCondition()) {
        delay(100)
    }
}