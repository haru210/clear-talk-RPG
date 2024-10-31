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
                    .addCallback(ScenarioDatabaseCallback(context, scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ScenarioDatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(context, database.scenarioDao())
                }
            }
        }

        /* TODO: 非常にハードコードしているのでjsonでparseして渡せるようにする */
        suspend fun populateDatabase(context: Context, scenarioDao: ScenarioDao) {
            /* シードデータの挿入 */
            val scenarios = mutableListOf<Scenario>()
            scenarios.add(0,Scenario(
                title = "呪われた迷宮 第1章",
                description = "高橋淳に誘われて街外れにある古びた神社に肝試しをしに行く。",
                jacketImage = R.mipmap.scenario1_image,
                timeRequired = 10,
                screens = mutableListOf(
                    Screen(
                        characterName = "ナレータ",
                        line = "あなたは男子中学生の春原圭です。\n" +
                                "あなたは怖いものが苦手で、でも優しいです。\n" +
                                "それではシナリオを開始します"
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "8月のある蒸し暑い夜。街外れにある古びた神社に、\n" +
                                "春原圭、高橋淳、椿山志穂の3人が肝試しに来ている。"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "何でこんなところ来ちゃったんだろう……。\n" +
                                "肝試しとかホント無理だって",
                        lineLength = 32,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "おいおい、圭。そんなビビるなって！\n" +
                                "怖いの苦手だからこそ、こういうの楽しめるんだよ"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "私も正直、怖いのはあんまりだけどさ……。\n" +
                                "でも、まあ、淳に誘われたし、来てみたけどね。"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "ほら、志穂も怖がりながらも来てくれたじゃん。\n" +
                                "じゃあ早速神社の奥に行ってみようぜ！"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "やっぱり帰りたいな……。\n" +
                                "でも、ここで引き返すのも男として情けないし……",
                        lineLength = 34,
                        isRecordingRequired = true
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "神社の境内を歩き始める三人。\n" +
                                "昼間は観光客も来るような場所だが、夜になると異様な雰囲気が漂っている"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "なんか……神社って言うより、墓場みたいじゃない？"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "それがいいんじゃん！\n" +
                                "ほら、こっちの方行ってみようぜ"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "え、そっちはダメだろ。\n" +
                                "御神体とか置いてある場所だって聞いたことあるし……。\n" +
                                "不敬じゃないか？",
                        lineLength = 42,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "いいからいいから、ただの噂だよ。\n" +
                                "夜の神社なんて、どこ行っても同じさ"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "あ、見て……。何か奥に通路みたいなのがあるかも……"
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "志穂が指差した先に、木々に覆われた暗い通路が見える。\n" +
                                "明らかに整備されていない古い石畳の道だ"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "なんだよこれ……こんな場所、昼間でも怖いのに",
                        lineLength = 21,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "やっぱり冒険って感じがしていいじゃん！\n" +
                                "志穂、よく見つけたな！"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "別にアタシが見つけたくて見つけたわけじゃないんだからね。\n" +
                                "アタシは怖いの苦手だからね。淳が先行ってよ"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "よーしゃあ俺が先頭な！\n" +
                                "みんな俺にちゃんとついて来いよ！"
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "三人は石畳の通路を奥へと進んでいく。\n" +
                                "薄暗い中、虫の鳴き声だけが響く。\n" +
                                "やがて道が広がり、神社の奥に隠されたような古い鳥居が姿を現す。"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "こ、こんなとこ、本当に入っていいのか？",
                        lineLength = 19,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "や、やっぱ帰ろうよ。\n" +
                                "こんな……知らない道、何があるかわからないしさ。"
                    ),
                    Screen(
                        backgroundImage = R.drawable.jinja_bg_2,
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "大丈夫、大丈夫！\n" +
                                "ほら、鳥居もあるし、ただの古い神社の一部だって。\n" +
                                "怖がらなくても平気だよ"
                    ),
                    Screen(
                        backgroundImage = R.drawable.jinja_bg_2,
                        characterName = "春原圭",
                        line = "淳、本当に大丈夫なんだろうな……？",
                        lineLength = 18,
                        isRecordingRequired = true
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "鳥居をくぐると、さらに細く曲がりくねった道が続いていた。\n" +
                                "その道を進んでいくと、次第に周囲の空気が冷たく、" +
                                "異様な雰囲気が漂う"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "ねぇ、なんか……空気が急に冷たくない？"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "う、うん。しかも、変な匂いがする。\n" +
                                "鉄のような……血の匂い？",
                        lineLength = 26,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "ふたりともビビりすぎだって。\n" +
                                "ここは誰も来ないから、自然にこういう感じになってるだけだろ。\n" +
                                "ほら、進もうぜ！"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "もう帰ろうよ……これ以上進むのは、まずいんじゃないか？",
                        lineLength = 19,
                        isRecordingRequired = true
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "突然、石畳が大きく崩れて、階段のような下り道が現れる。\n" +
                                "淳が足を滑らせ、階段に転がり込む"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "きゃっ！淳、大丈夫！？"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "うわっ！痛ってぇ……。\n" +
                                "ん？　なんか地下に続いてるみたいだぞ！\n" +
                                "行ってみようぜ！"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "えぇっ！？　まだ行くの！？"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "でも、ここで淳だけ行かせるのも危ないし……",
                        lineLength = 20,
                        isRecordingRequired = true
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "三人は階段を降りて地下の迷宮に入る。\n" +
                                "暗く冷たい石壁に囲まれ、明かりは圭が持っている小さな懐中電灯だけ。\n" +
                                "足音が響き、誰もいないはずなのに、影が揺れているように見える"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "ねぇ、本当に何もないんだよね……？"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "ああ、こういうところは雰囲気だけだって。\n" +
                                "何もないって……え？"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "……どうした？",
                        lineLength = 4,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "さっきから誰かの視線を感じる気がするんだけど\n" +
                                "……いや、気のせいかな"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "やだ、やっぱり帰ろうよ！もう無理だってば！"
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "迷宮の奥に進むと、古ぼけた祭壇が見えてくる。\n" +
                                "何かが置かれていた跡があり、\n" +
                                "その周囲には見覚えのない文字が刻まれている。"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "ねえ、不気味過ぎない？　壁にすっごくお札貼られてるんだけど"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "まさかこれ、何かの儀式に使われてたんじゃ？",
                        lineLength = 21,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "確かに、この感じ……何か封じられてたのかもな。\n" +
                                "でも、今は何も残ってないみたいだし"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "やめてよ！　そんな怖いこと言わないでよ！"
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "突然、後ろから誰かが階段を降りてくるような足音が響く。\n" +
                                "三人は一斉に振り向くが、誰もいない"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "ねえ……今の足音、何？"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "誰もいないはずだよな……でも、今、確かに",
                        lineLength = 19,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "わかったわかった、そろそろ帰ろう。\n" +
                                "俺にもここがヤバいってことは雰囲気でわかる"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "うん、賛成だ。　俺ももう限界だ……",
                        lineLength = 17,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "嘘でしょ……この道、さっき通らなかった？"
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.takahashi_jun,
                        characterName = "高橋淳",
                        line = "冗談だろ！？な、なんで出口が……"
                    ),
                    Screen(
                        characterName = "春原圭",
                        line = "これ、まさか本当に……閉じ込められたんじゃないか？",
                        lineLength = 22,
                        isRecordingRequired = true
                    ),
                    Screen(
                        characterSpriteMiddle = R.drawable.girl_undarkened,
                        characterName = "椿山志穂",
                        line = "やだ、もう……助けて……お願い、ここから出して！"
                    ),
                    Screen(
                        backgroundImage = R.drawable.title_screen_background_image,
                        line = "闇に飲み込まれるように、三人の叫び声が迷宮の中に消えていく。\n" +
                                "出口のない、永遠の恐怖が彼らを待ち構えていた"
                    )
                )
            ))

            scenarios.forEach { scenario ->
                scenarioDao.post(scenario)
            }
        }
    }
}
