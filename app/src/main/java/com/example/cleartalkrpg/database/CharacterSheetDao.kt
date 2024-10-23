package com.example.cleartalkrpg.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterSheetDao {
    @Query("select * from charactersheets order by id asc")
    fun getAll(): Flow<List<CharacterSheet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun post(characterSheet: CharacterSheet)

    @Update
    suspend fun update(characterSheet: CharacterSheet)

    @Delete
    suspend fun delete(characterSheet: CharacterSheet)
}
