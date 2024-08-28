package com.example.cleartalkrpg.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ResultDao {
    @Query("select * from results order by created_at asc")
    fun getAll(): MutableList<Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun post(result: Result)

    @Delete
    fun delete(result: Result)
}
