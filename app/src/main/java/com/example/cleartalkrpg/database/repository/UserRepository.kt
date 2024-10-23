package com.example.cleartalkrpg.database.repository

import com.example.cleartalkrpg.database.CharacterSheet
import com.example.cleartalkrpg.database.CharacterSheetDao
import com.example.cleartalkrpg.database.ResultDao
import com.example.cleartalkrpg.database.Result
import com.example.cleartalkrpg.database.ScenarioDao
import com.example.cleartalkrpg.database.Scenario
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val resultDao: ResultDao,
    private val scenarioDao: ScenarioDao,
    private val characterSheetDao: CharacterSheetDao
) {
    val allResults: Flow<List<Result>> = resultDao.getAll()
    val allScenarios: Flow<List<Scenario>> = scenarioDao.getAll()
    val allCharacterSheets: Flow<List<CharacterSheet>> = characterSheetDao.getAll()

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
    suspend fun update(scenario: Scenario) {
        scenarioDao.update(scenario)
    }
    suspend fun delete(scenario: Scenario) {
        scenarioDao.delete(scenario)
    }

    /* CharacterSheetテーブルの操作関数 */
    suspend fun post(characterSheet: CharacterSheet) {
        characterSheetDao.post(characterSheet)
    }
    suspend fun update(characterSheet: CharacterSheet) {
        characterSheetDao.update(characterSheet)
    }
    suspend fun delete(characterSheet: CharacterSheet) {
        characterSheetDao.delete(characterSheet)
    }
}
