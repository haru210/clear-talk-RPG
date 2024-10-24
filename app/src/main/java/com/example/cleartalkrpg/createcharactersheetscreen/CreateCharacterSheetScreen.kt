package com.example.cleartalkrpg.createcharactersheetscreen

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.database.CharacterSheet
import com.example.cleartalkrpg.viewmodel.CharacterSheetViewModel

@Composable
fun CreateCharacterSheetScreen(
    navController: NavController,
    characterSheetViewModel: CharacterSheetViewModel
) {
    val characterSheet by remember { mutableStateOf<CharacterSheet?>(null) }
    val nameState = remember { mutableStateOf("") }
    val occupationState = remember { mutableStateOf("") }
    val genderState = remember { mutableStateOf("") }
    val ageState = remember { mutableStateOf("") }
    val hometownState = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomTopBar(onBackClick = { navController.navigate(ClearTalkRPGScreen.Home.name) })
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            OutlinedTextField(label = "名前", textState = nameState)
            OutlinedTextField(label = "性別", textState = genderState)
            OutlinedTextField(label = "年齢", textState = ageState)
            OutlinedTextField(label = "出身地", textState = hometownState)
        }
        CreateCharacterSheetButton(onClick = {

        })
    }
}

@Composable
fun CustomTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray.copy(alpha = 0.65f))
            .padding(4.dp)
            .clickable { onBackClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "ホーム画面へ",
            fontFamily = FontFamily(Font(R.font.koruri_regular)),
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(8.dp, 0.dp)
        )
        Text(
            text = "新規キャラクター作成",
            fontFamily = FontFamily(Font(R.font.koruri_regular)),
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(8.dp, 0.dp)
        )
    }
}

@Composable
fun CreateCharacterSheetButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(
                text = "新規キャラクター作成",
                fontFamily = FontFamily(Font(R.font.koruri_bold)),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun OutlinedTextField(
    label: String,
    textState: MutableState<String>
) {
    TextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        label = { Text(text = label) }
    )
}
