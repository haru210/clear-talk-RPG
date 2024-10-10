package com.example.cleartalkrpg.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

/* colors created by nash */
val n_Gold = Color(0xFFFFD700)
val n_Green = Color(0xFF4CAF50)
val n_Blue = Color(0xFF2196F3)
val n_Orange = Color(0xFFFF9800)
val n_DarkGray = Color(0xFF424242)
val n_Yellow = Color(0xFFFFEB3B)

val n_GoldGradient = Brush.horizontalGradient(colors = listOf(n_Gold, Color(0xFFFFA500)))
val n_GreenGradient = Brush.horizontalGradient(colors = listOf(n_Green, Color(0xFF2E7D32)))
val n_BlueGradient = Brush.horizontalGradient(colors = listOf(n_Blue, Color(0xFF1976D2)))
val n_OrangeGradient = Brush.horizontalGradient(colors = listOf(n_Orange, Color(0xFFF57C00)))
val n_DarkGrayGradient = Brush.horizontalGradient(colors = listOf(n_DarkGray, Color.Black))

sealed class n_BackgroundColor {
    data class SolidColor(val color: Color) : n_BackgroundColor()
    data class Gradient(val brush: Brush) : n_BackgroundColor()
}

sealed class n_FontColor {
    data class SolidColor(val color: Color) : n_FontColor()
}
