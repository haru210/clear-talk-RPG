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
            scenarios.add(0, Scenario(
                title = "チュートリアル",
                description = "ClearTalk RPGを始める探索者のキミたちへ送る、ボクのチュートリアル",
                jacketImage = R.drawable.tutorial,
                timeRequired = 2,
                screens = mutableListOf(
                    Screen(
                        characterName = "？？？",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "こんにちは。ボクはチューターのロココ。\n" +
                                "チュートリアルを始めるよ",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "まず、キミのお名前を知りたいな。\n" +
                                "大きな声でボクに伝わるように言ってみて",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "あなた",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "こんにちは。私は<selectedCharacterName>だよ",
                        isRecordingRequired = true,
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "うーん。聞こえないなぁ......",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "あっ！マイクがオフになってた。\n" +
                                "マイクをオンにするよ",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "右上にマイクのアイコンが表示されるから、\n" +
                                "そのときに喋ってみて。\n" +
                                "もう一度お名前、教えてくれない？",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "あなた",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "私は<selectedCharacterName>だよ",
                        isRecordingRequired = true,
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "うん！聞こえたよ！<selectedCharacterName>だね\n" +
                                "今からゲームの説明を始めるね",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "右上にマイクのアイコンが表示されてる時、\n" +
                                "下のメッセージを読み上げてね",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "別にメッセージを全く同じように読まなくてもいい。\n" +
                                "自分のキャラクターにあった喋り方で話してね。\n" +
                                "これはロールプレイの基本なの",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "ドロイドくんなら「ワタシハドロイドデス」、\n" +
                                "かまきりりゅうじなら「おう　おれはかまきりだぜ」って\n" +
                                "言ってみるといいかも！",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "じゃあ、試しに挨拶してみて",
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "あなた",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "よろしくお願いします",
                        isRecordingRequired = true,
                        isSelectedCharacterStanding = true
                    ),
                    Screen(
                        characterName = "ロロコ",
                        characterSpriteLeft = null,
                        characterSpriteRight = R.drawable.roroco,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "よろしくね！",
                        isSelectedCharacterStanding = true
                    )
                )
            ))
            scenarios.add(1,Scenario(
                title = "おれはかまきり",
                description = "かまきり りゅうじによる詩",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 1,
                screens = mutableListOf<Screen>(
                    Screen(
                        characterName = "かまきり",
                        characterSpriteRight = R.drawable.kamakiri,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "おう　なつだぜ\n" +
                                "おれは　げんきだぜ\n" +
                                "あまりちかよるな",
                        lineLength = 22
                    ),
                    Screen(
                        characterName = "かまきり",
                        characterSpriteRight = R.drawable.kamakiri,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "おれの　こころも　かまも\n" +
                                "どきどきするほど\n" +
                                "ひかっているぜ",
                        lineLength = 25
                    ),
                    Screen(
                        characterName = "かまきり",
                        characterSpriteRight = R.drawable.kamakiri,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "おう　あついぜ\n" +
                                "おれは　がんばるぜ\n" +
                                "もえる　ひをあびて",
                        lineLength = 22
                    ),
                    Screen(
                        characterName = "かまきり",
                        characterSpriteRight = R.drawable.kamakiri,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "かまを　ふりかざす　すがた\n" +
                                "わくわくするほど\n" +
                                "きまってるぜ",
                        lineLength = 25
                    )
                )
            ))
            scenarios.add(2,Scenario(
                title = "テセウスの心臓",
                description = "失った心臓。それは己を犠牲にしてまで愛した心臓。誰が盗んだ？",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 15,
                screens = mutableListOf<Screen>()
            ))
            scenarios.add(3,Scenario(
                title = "イル＝ペコローネIV 人形劇",
                description = "ノブレス＝オブリージュを忘れた愚者は劇場の舞台で踊らされ、審判の裁定を待つばかりである。",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 40,
                screens = mutableListOf<Screen>()
            ))
            scenarios.add(4,Scenario(
                title = "リミナルスペースの患者",
                description = "静謐な虚空と久遠の一瞬《トキ》、紡がれる記憶は身を滅ぼす。",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 20,
                screens = mutableListOf<Screen>()
            ))
            scenarios.add(5,Scenario(
                title = "雨のち小夜曲",
                description = "雫が世界に最後の響きを齎すとき、その儚き少女は復讐の舞を舞う。",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 15,
                screens = mutableListOf<Screen>()
            ))
            scenarios.add(6,Scenario(
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
            scenarios.add(7,Scenario(
                title = "こんにちはするだけ",
                description = "こんにちはするだけ",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 1,
                screens = mutableListOf<Screen>(
                    Screen(
                        characterName = "You",
                        characterSpriteMiddle = null,
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "こんにちは",
                        lineLength = 5,
                        isRecordingRequired = true
                    ),
                )
            ))
            scenarios.add(8,Scenario(
                title = "牢獄からの脱出",
                description = "起きたらそこは薄暗い牢獄だった。廊下を進むと、そこには見知らぬ少女がいた。",
                jacketImage = R.drawable.prison,
                timeRequired = 20,
                screens = mutableListOf(
                    Screen(
                        characterName = "？？？",
                        characterSpriteMiddle = R.drawable.girl_darken,
                        backgroundImage = R.drawable.prison,
                        line = "ちょっ！静かにして！"
                    ),
                    Screen(
                        characterName = "？？？",
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        backgroundImage = R.drawable.prison,
                        line = "私も気づいたらこの牢獄にいたの......"
                    )
                )
            ))

            scenarios.forEach { scenario ->
                scenarioDao.post(scenario)
            }
        }
    }
}