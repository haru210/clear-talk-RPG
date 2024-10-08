package com.example.cleartalkrpg.database

import android.content.Context
import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cleartalkrpg.utils.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
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
            val results = mutableListOf<Result>()
            results.add(0, Result(
                scenario_title = "おれはかまきり",
                total_score = 89,
                volume_score = 28,
                clarity_score = 38,
                speed_score = 23,
                comment = "もう少しゆっくり一言一言大切に話してみましょう！",
                created_at = Date()
            ))
            results.add(1, Result(
                scenario_title = "テセウスの心臓",
                total_score = 95,
                volume_score = 27,
                clarity_score = 39,
                speed_score = 29,
                comment = "とても聞き取りやすい声を出せています。もう一息です！",
                created_at = Date()
            ))
            results.add(2, Result(
                scenario_title = "イル＝ペコローネIV 人形劇",
                total_score = 59,
                volume_score = 19,
                clarity_score = 20,
                speed_score = 20,
                comment = "高専なら欠点です。息を吐きながら声を届けることを重点的に意識してみましょう。",
                created_at = Date()
            ))
            results.add(3, Result(
                scenario_title = "リミナルスペースの患者",
                total_score = 20,
                volume_score = 30,
                clarity_score = 35,
                speed_score = 25,
                comment = "とても聞き取りやすい声を出せています。もう一息です！",
                created_at = Date()
            ))
            results.add(4, Result(
                scenario_title = "雨のち小夜曲",
                total_score = 84,
                volume_score = 23,
                clarity_score = 33,
                speed_score = 28,
                comment = "もう少しゆっくり一言一言大切に話してみましょう！",
                created_at = Date()
            ))
            results.forEach { result ->
                resultDao.post(result)
            }
        }
    }
}