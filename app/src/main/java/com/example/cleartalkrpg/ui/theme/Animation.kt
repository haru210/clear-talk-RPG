package com.example.cleartalkrpg.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cleartalkrpg.R

@Composable
fun RectangleSpinningAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.rectangle_spinning_animation))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun DownpointingTriangleAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.downpointing_triangle_animation))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        contentScale = ContentScale.FillBounds
    )
}
