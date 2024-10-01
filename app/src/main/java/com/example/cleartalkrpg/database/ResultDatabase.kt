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
            /* シードデータの挿入 */
            val testResult = Result(
                scenario_title = "おれはかまきり",
                total_score = 89,
                volume_score = 28,
                clarity_score = 38,
                speed_score = 23,
                comment = "もう少しゆっくり一言一言大切に話してみましょう！",
                created_at = Date()
            )
            val testResult2 = Result(
                scenario_title = "テセウスの心臓",
                total_score = 95,
                volume_score = 27,
                clarity_score = 39,
                speed_score = 29,
                comment = "とても聞き取りやすい声を出せています。もう一息です！",
                created_at = Date()
            )
            resultDao.post(testResult)
            resultDao.post(testResult2)
        }
    }
}