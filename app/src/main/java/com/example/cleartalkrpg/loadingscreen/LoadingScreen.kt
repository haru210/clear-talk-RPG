package com.example.cleartalkrpg.loadingscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.ui.theme.RectangleSpinningAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingScreen(navigation: () -> Unit) {
    var loading by remember { mutableStateOf(false) }

    loading = true

    LaunchedEffect(loading) {
        if (loading) {
            delay(5000)
            loading = false
            navigation()
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.padding(16.dp)
        ) {
            LoadingAnimation()
        }
    }
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
