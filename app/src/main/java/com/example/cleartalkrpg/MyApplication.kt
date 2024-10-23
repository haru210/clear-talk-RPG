package com.example.cleartalkrpg

import android.app.Application
import com.example.cleartalkrpg.database.CharacterSheetDatabase
import com.example.cleartalkrpg.database.ResultDatabase
import com.example.cleartalkrpg.database.ScenarioDatabase
import com.example.cleartalkrpg.database.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CTRPGApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ResultDatabase.getDatabase(this, applicationScope)}
    val database2 by lazy { ScenarioDatabase.getDatabase(this, applicationScope)}
    val database3 by lazy { CharacterSheetDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { UserRepository(database.resultDao(), database2.scenarioDao(), database3.characterSheetDao()) }
}