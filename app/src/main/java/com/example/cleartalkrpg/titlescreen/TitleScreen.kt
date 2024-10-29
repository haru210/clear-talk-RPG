package com.example.cleartalkrpg.titlescreen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.ui.theme.BackgroundImage
import com.example.cleartalkrpg.ui.theme.ClearTalkRPGLogoAnimation
import kotlinx.coroutines.delay

@Composable
fun TitleScreen(navController: NavController) {
    var loading by remember { mutableStateOf(true) }
    var startBlinking by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        delay(6000)
        loading = false
        startBlinking = true
    }

    // 点滅用のアニメーション
    val alphaAnimation = rememberInfiniteTransition().animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500), // 点滅速度を500msに設定
            repeatMode = RepeatMode.Reverse
        )
    )

    BackgroundImage(id = R.drawable.spring_school_afternoon, alt = "タイトル画面背景")
    Box(
        modifier = Modifier
            .clickable { navController.navigate(ClearTalkRPGScreen.Home.name) }
            .drawBehind {
                val borderSize = 20.dp.toPx()
                val borderColor = Color.White

                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = borderSize
                )

                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
    ) {
        TitleLogo()
        if (!loading && startBlinking) {
            TapToStart(modifier = Modifier.alpha(alphaAnimation.value))
        }
    }
}

/* タイトルロゴ */
@Composable
fun TitleLogo() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .offset(0.dp, (-10).dp)
    ) {
        /* タイトル */
        ClearTalkRPGLogoAnimation()
    }
}

/* タイトルロゴ下部の画面タップを促すテキスト */
@Composable
fun TapToStart(modifier: Modifier) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 84.dp)
    ) {
        Text(
            text = "tap to start",
            fontFamily = FontFamily(Font(R.font.koruri_bold)),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight(1000),
            modifier = modifier
        )
        Text(
            text = "tap to start",
            fontFamily = FontFamily(Font(R.font.koruri_bold)),
            fontSize = 24.sp,
            color = Color.Black,
            modifier = modifier
        )
    }
}
