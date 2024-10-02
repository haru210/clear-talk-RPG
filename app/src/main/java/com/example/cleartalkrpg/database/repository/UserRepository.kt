package com.example.cleartalkrpg.database.repository

import com.example.cleartalkrpg.database.ResultDao
import com.example.cleartalkrpg.database.Result
import com.example.cleartalkrpg.database.ScenarioDao
import com.example.cleartalkrpg.database.Scenario
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val resultDao: ResultDao,
    private val scenarioDao: ScenarioDao
) {
    val allResults: Flow<List<Result>> = resultDao.getAll()
    val allScenarios: Flow<List<Scenario>> = scenarioDao.getAll()

    /* Resultテーブルの操作関数 */
    suspend fun post(result: Result) {
        resultDao.post(result)
    }
    suspend fun delete(result: Result) {
        resultDao.delete(result)
    }

    /* Scenarioテーブルの操作関数 */
    suspend fun post(scenario: Scenario) {
        scenarioDao.post(scenario)
    }
    suspend fun delete(scenario: Scenario) {
        scenarioDao.delete(scenario)
    }
}
