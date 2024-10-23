package com.example.cleartalkrpg.viewmodel

import androidx.lifecycle.*
import com.example.cleartalkrpg.database.CharacterSheet
import com.example.cleartalkrpg.database.repository.UserRepository
import kotlinx.coroutines.launch

class CharacterSheetViewModel(private val repository: UserRepository) : ViewModel() {

    val allCharacterSheets: LiveData<List<CharacterSheet>> = repository.allCharacterSheets.asLiveData()

    fun post(characterSheet: CharacterSheet) = viewModelScope.launch {
        repository.post(characterSheet)
    }

    fun update(characterSheet: CharacterSheet) = viewModelScope.launch {
        repository.update(characterSheet)
    }

    fun delete(characterSheet: CharacterSheet) = viewModelScope.launch {
        repository.delete(characterSheet)
    }
}

class CharacterSheetViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(CharacterSheetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterSheetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}