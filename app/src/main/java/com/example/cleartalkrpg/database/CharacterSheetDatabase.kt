package com.example.cleartalkrpg.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
                    "charactersheet_database"
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
        }
    }
}