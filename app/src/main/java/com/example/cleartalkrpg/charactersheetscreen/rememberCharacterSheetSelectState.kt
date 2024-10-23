package com.example.cleartalkrpg.charactersheetscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.cleartalkrpg.database.CharacterSheet
import com.example.cleartalkrpg.viewmodel.CharacterSheetViewModel

@Composable
fun rememberCharacterSheetSelectState(
    characterSheetViewModel: CharacterSheetViewModel
): CharacterSheetSelectState {
    val characterSheets by characterSheetViewModel.allCharacterSheets.observeAsState(mutableListOf())
    val selectedCharacter = remember {
        mutableStateOf(if(characterSheets.isNotEmpty()) characterSheets.first() else null)
    }
    LaunchedEffect(characterSheets) {
        if (characterSheets.isNotEmpty()) {
            selectedCharacter.value = characterSheets.first()
        } else {
            selectedCharacter.value = null
        }
    }

    return CharacterSheetSelectState(
        characterSheets = characterSheets,
        selectedCharacter = selectedCharacter.value,
        onCharacterSheetSelected = { characterSheet -> selectedCharacter.value = characterSheet }
    )
}

data class CharacterSheetSelectState(
    val characterSheets: List<CharacterSheet>,
    val selectedCharacter: CharacterSheet?,
    val onCharacterSheetSelected: (CharacterSheet) -> Unit
)
