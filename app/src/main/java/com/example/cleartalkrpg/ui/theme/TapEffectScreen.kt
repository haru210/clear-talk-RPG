package com.example.cleartalkrpg.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun TapEffectScreen() {
    val tapPositions = remember { mutableStateListOf<Offset>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { tapPosition ->
                    tapPositions.add(tapPosition)
                }
            }
    ) {
        tapPositions.forEach { position ->
            PopEffect(position = position) {
                tapPositions.remove(position)
            }
        }
    }
}

@Composable
fun PopEffect(position: Offset, onFinished: () -> Unit) {
    var radius by remember { mutableStateOf(100f) }
    var alpha by remember { mutableStateOf(1f) }

    val animatedRadius by animateFloatAsState(
        targetValue = radius,
        animationSpec = tween(durationMillis = 500),
        label = "FloatAnimation"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(durationMillis = 500),
        finishedListener = { onFinished() },
        label = "FloatAnimation"
    )

    LaunchedEffect(Unit) {
        radius = 0f
        alpha = 0f
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawCircle(
            color = Color.Magenta.copy(alpha = animatedAlpha),
            radius = animatedRadius,
            center = position
        )
    }
}
