package com.example.cleartalkrpg.selectcharacterscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cleartalkrpg.ClearTalkRPGScreen
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.viewmodel.CharacterSheetViewModel
import kotlinx.coroutines.delay

@Composable
fun SelectCharacterScreen(
    navController: NavController,
    characterSheetViewModel: CharacterSheetViewModel,
    characterSheetSelectState: CharacterSheetSelectState
) {
    val characterSheets by characterSheetViewModel.allCharacterSheets.observeAsState(mutableListOf())
    val listState = rememberLazyListState()
    val centerIndex = remember {
        derivedStateOf {
            val firstVisibleItem = listState.firstVisibleItemIndex
            val offset = listState.firstVisibleItemScrollOffset

            if (offset > listState.layoutInfo.viewportEndOffset / 2) {
                (firstVisibleItem + 1).coerceIn(0, characterSheets.size - 1)
            } else {
                firstVisibleItem.coerceIn(0, characterSheets.size - 1)
            }
        }
    }

    val centerCharacter = characterSheets[centerIndex.value]

    var feedbackMessageVisible by remember { mutableStateOf(false) }

    LaunchedEffect(feedbackMessageVisible) {
        delay(2000)
        feedbackMessageVisible = false
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomTopBar(onBackClick = { navController.navigate(ClearTalkRPGScreen.Home.name) })
            Box(modifier = Modifier.fillMaxSize()) {
                /* キャラクター名を表示 */
                Text(
                    text = centerCharacter.name ?: "",
                    fontFamily = FontFamily(Font(R.font.koruri_bold)),
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(160.dp, 16.dp)
                )

                LazyRow(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(horizontal = 215.dp)
                ) {
                    items(characterSheets.size) { index ->
                        val character = characterSheets[index]

                        Image(
                            painter = painterResource(id = character.sprite ?: 0), // スプライトのリソースIDを使用
                            contentDescription = character.name,
                            modifier = Modifier
                                .size(300.dp)
                                .padding(vertical = 50.dp),
                            contentScale = ContentScale.Inside
                        )
                    }
                }

                Button(
                    onClick = {
                        characterSheetSelectState.onCharacterSheetSelected(centerCharacter)
                        feedbackMessageVisible = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "キャラクターを選択する",
                        fontFamily = FontFamily(Font(R.font.koruri_bold)),
                        fontSize = 16.sp
                    )
                }
            }
        }
        if (feedbackMessageVisible) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.background(Color.DarkGray.copy(0.65f), RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "使用するキャラクターを ${characterSheetSelectState.selectedCharacter?.name} に設定しました。",
                        fontFamily = FontFamily(Font(R.font.koruri_bold)),
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
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
            text = "キャラクター選択",
            fontFamily = FontFamily(Font(R.font.koruri_regular)),
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier
                .weight(1f, fill = false)
                .padding(8.dp, 0.dp)
        )
    }
}
