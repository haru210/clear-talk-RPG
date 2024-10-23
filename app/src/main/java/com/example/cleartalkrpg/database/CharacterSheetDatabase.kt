package com.example.cleartalkrpg.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cleartalkrpg.R
import com.example.cleartalkrpg.utils.Gender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CharacterSheet::class], version = 1, exportSchema = false)
abstract class CharacterSheetDatabase: RoomDatabase() {
    abstract fun characterSheetDao(): CharacterSheetDao

    companion object {
        @Volatile
        private var INSTANCE: CharacterSheetDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CharacterSheetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterSheetDatabase::class.java,
                    "character_sheet_database"
                )
                    .addCallback(CharacterSheetDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class CharacterSheetDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.characterSheetDao())
                }
            }
        }

        suspend fun populateDatabase(characterSheetDao: CharacterSheetDao) {
            /* シードデータの挿入 */
            val characterSheets = mutableListOf<CharacterSheet>()
            characterSheets.add(0,
                CharacterSheet(
                    name = "ドロイド君",
                    occupation = "アンドロイド",
                    sprite = R.drawable.droid_kun,
                    description = "Androidのマスコットキャラクター",
                    gender = Gender.None,
                    age = null,
                    hometown = "San Francisco",
                    STR = 0,
                    CON = 0,
                    POW = 0,
                    DEX = 0,
                    APP = 0,
                    SIZ = 0,
                    INT = 0,
                    EDU = 0,
                    PRO = 0,
                    SAN = 0,
                    LUCK = 0,
                    IDEA = 0,
                    KNOW = 0,
                    PAT = 0,
                    MAGP = 0,
                    OCCP = 0,
                    HOBP = 0,
                    DMGB = 0
                )
            )

            characterSheets.forEach { characterSheet ->
                characterSheetDao.post(characterSheet)
            }
        }
    }
}