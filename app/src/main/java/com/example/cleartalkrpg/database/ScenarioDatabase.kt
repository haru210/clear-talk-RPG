package com.example.cleartalkrpg.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.utils.ScreenListConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Scenario::class], version = 1, exportSchema = false)
@TypeConverters(ScreenListConverter::class)
abstract class ScenarioDatabase: RoomDatabase() {
    abstract fun scenarioDao(): ScenarioDao

    companion object {
        @Volatile
        private var INSTANCE: ScenarioDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ScenarioDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScenarioDatabase::class.java,
                    "scenario_database"
                )
                    .addCallback(ScenarioDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ScenarioDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.scenarioDao())
                }
            }
        }

        /* TODO: 非常にハードコードしているのでjsonでparseして渡せるようにする */
        suspend fun populateDatabase(scenarioDao: ScenarioDao) {
            /* シードデータの挿入 */
            val scenarios = mutableListOf<Scenario>()
            scenarios.add(0,Scenario(
                title = "ポラーノの広場 節",
                description = "宮沢賢治の短編小説",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 1,
                screens = mutableListOf<Screen>(
                    Screen(
                        characterName = "GM",
                        characterSpriteMiddle = null,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "あのイーハトーヴォのすきとおった風、\n" +
                                "夏でも底に冷たさをもつ青いそら、\n" +
                                "うつくしい森で飾られたモリーオ市、郊外のぎらぎら光る草の波。",
                        lineLength = 70
                    ),
                )
            ))

            scenarios.forEach { scenario ->
                scenarioDao.post(scenario)
            }
        }
    }
}
