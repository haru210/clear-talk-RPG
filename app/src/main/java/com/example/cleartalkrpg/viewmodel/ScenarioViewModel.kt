package com.example.cleartalkrpg.viewmodel

import androidx.lifecycle.*
import com.example.cleartalkrpg.database.Scenario
import com.example.cleartalkrpg.database.repository.UserRepository
import kotlinx.coroutines.launch

class ScenarioViewModel(private val repository: UserRepository) : ViewModel() {

    val allScenarios: LiveData<List<Scenario>> = repository.allScenarios.asLiveData()

    fun post(scenario: Scenario) = viewModelScope.launch {
        repository.post(scenario)
    }

    fun delete(scenario: Scenario) = viewModelScope.launch {
        repository.delete(scenario)
    }
}

class ScenarioViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(ScenarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScenarioViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}