package com.example.cleartalkrpg.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class ResultDatabase: RoomDatabase() {
    abstract fun resultDao(): ResultDao

    companion object {
        @Volatile
        private var INSTANCE: ResultDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ResultDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ResultDatabase::class.java,
                    "result_database"
                )
                    .addCallback(ResultDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ResultDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.resultDao())
                }
            }
        }

        suspend fun populateDatabase(resultDao: ResultDao) {
            //TODO: シードデータの挿入
            val testResult = Result(
                scenario_title = "A",
                total_score = 100,
                volume_score = 30,
                clarity_score = 40,
                speed_score = 30,
                comment = "Good",
                created_at = Date(100)
            )
            val testResult2 = Result(
                scenario_title = "B",
                total_score = 0,
                volume_score = 0,
                clarity_score = 0,
                speed_score = 0,
                comment = "F",
                created_at = Date(1727429788565)
            )
            resultDao.post(testResult)
            resultDao.post(testResult2)
        }
    }
}