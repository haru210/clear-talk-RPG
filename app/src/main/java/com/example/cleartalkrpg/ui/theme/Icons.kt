package com.example.cleartalkrpg.ui.theme

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.cleartalkrpg.R

@Composable
fun HistoryIcon(iconColor: Color, iconSize: Dp) {
    Icon(
        painter = painterResource(id = R.drawable.history_icon),
        contentDescription = "History icon",
        tint = iconColor,
        modifier = Modifier.size(iconSize)
    )
}

@Composable
fun PauseIcon(iconColor: Color, iconSize: Dp) {
    Icon(
        painter = painterResource(id = R.drawable.pause_circle),
        contentDescription = "Pause Icon",
        tint = iconColor,
        modifier = Modifier.size(iconSize)
    )
}
