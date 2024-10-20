package com.example.cleartalkrpg.loadingscreen

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ui.theme.RectangleSpinningAnimation
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(navigation: () -> Unit) {
    var loading by remember { mutableStateOf(false) }

    loading = true

    LaunchedEffect(loading) {
        if (loading) {
            delay(3000)
            loading = false
            navigation()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(12.dp, 24.dp)
            ) {
                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    CautionCutinText(
                        textContent = "シナリオ本編の際は画面から30cmほど離れてゲームをお楽しみください。"
                    )
                }
                Box(modifier = Modifier.align(Alignment.End)) {
                    LoadingAnimation()
                }
            }
        }
    }
}

@Composable
fun CautionCutinVideo() {
    AndroidView(
        factory = { context ->
            VideoView(context).apply {
                val videoUri = Uri.parse("android.resource://${context.packageName}/raw/caution_cutin")
                setVideoURI(videoUri)
                setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = true
                    start()
                }
            }
        }, modifier = Modifier.fillMaxSize())
}

@Composable
fun CautionCutinText(textContent: String) {
    Text(
        text = textContent,
        fontFamily = FontFamily(Font(resId = R.font.koruri_bold)),
        fontSize = 18.sp,
        color = Color.DarkGray
    )
}

@Composable
fun LoadingAnimation() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Now Loading")
        Box(modifier = Modifier.size(24.dp)) {
            RectangleSpinningAnimation()
        }
    }
}
