package com.example.cleartalkrpg.viewmodel

import androidx.lifecycle.*
import com.example.cleartalkrpg.database.repository.UserRepository
import com.example.cleartalkrpg.database.Result
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: UserRepository) : ViewModel() {

    val allResults: LiveData<List<Result>> = repository.allResults.asLiveData()

    fun post(result: Result) = viewModelScope.launch {
        repository.post(result)
    }

    fun delete(result: Result) = viewModelScope.launch {
        repository.delete(result)
    }
}

class ResultViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if(modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}