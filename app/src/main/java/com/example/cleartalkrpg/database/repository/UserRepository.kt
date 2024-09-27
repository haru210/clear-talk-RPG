package com.example.cleartalkrpg.database.repository

import com.example.cleartalkrpg.database.ResultDao
import com.example.cleartalkrpg.database.Result
import kotlinx.coroutines.flow.Flow

class ResultRepository(private val resultDao: ResultDao) {
    val allResults: Flow<List<Result>> = resultDao.getAll()

    suspend fun post(result: Result) {
        resultDao.post(result)
    }
    suspend fun delete(result: Result) {
        resultDao.delete(result)
    }
}