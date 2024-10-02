package com.example.cleartalkrpg.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScenarioDao {
    @Query("select * from scenarios order by id asc")
    fun getAll(): Flow<List<Scenario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun post(scenario: Scenario)

    @Delete
    fun delete(scenario: Scenario)
}
