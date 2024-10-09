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

        suspend fun populateDatabase(scenarioDao: ScenarioDao) {
            /* シードデータの挿入 */
            val scenarios = mutableListOf<Scenario>()
            scenarios.add(0,Scenario(
                title = "おれはかまきり",
                description = "かまきり りゅうじによる詩",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 1,
                screens = mutableListOf<Screen>(
                    Screen(
                        characterName = "かまきり",
                        characterSprite = R.drawable.kamakiri,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "おう　なつだぜ\n" +
                                "おれは　げんきだぜ\n" +
                                "あまりちかよるな",
                        lineLength = 22
                    ),
                    Screen(
                        characterName = "かまきり",
                        characterSprite = R.drawable.kamakiri,
                        backgroundImage = R.mipmap.scenario1_image,
                        line = "おれの　こころも　かまも\n" +
                                "どきどきするほど\n" +
                                "ひかっているぜ",
                        lineLength = 25
                    ),
                    Screen(
                        characterName = "かまきり",
                        characterSprite = R.drawable.kamakiri,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "おう　あついぜ\n" +
                                "おれは　がんばるぜ\n" +
                                "もえる　ひをあびて",
                        lineLength = 22
                    ),
                    Screen(
                        characterName = "かまきり",
                        characterSprite = R.drawable.kamakiri,
                        backgroundImage = R.mipmap.scenario1_image,
                        line = "かまを　ふりかざす　すがた\n" +
                                "わくわくするほど\n" +
                                "きまってるぜ",
                        lineLength = 25
                    )
                )
            ))
            scenarios.add(1,Scenario(
                title = "テセウスの心臓",
                description = "失った心臓。それは己を犠牲にしてまで愛した心臓。誰が盗んだ？",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 15,
                screens = mutableListOf<Screen>()
            ))
            scenarios.add(2,Scenario(
                title = "イル＝ペコローネIV 人形劇",
                description = "ノブレス＝オブリージュを忘れた愚者は劇場の舞台で踊らされ、審判の裁定を待つばかりである。",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 40,
                screens = mutableListOf<Screen>()
            ))
            scenarios.add(3,Scenario(
                title = "リミナルスペースの患者",
                description = "静謐な虚空と久遠の一瞬《トキ》、紡がれる記憶は身を滅ぼす。",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 20,
                screens = mutableListOf<Screen>()
            ))
            scenarios.add(4,Scenario(
                title = "雨のち小夜曲",
                description = "雫が世界に最後の響きを齎すとき、その儚き少女は復讐の舞を舞う。",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 15,
                screens = mutableListOf<Screen>()
            ))
            scenarios.forEach { scenario ->
                scenarioDao.post(scenario)
            }
        }
    }
}