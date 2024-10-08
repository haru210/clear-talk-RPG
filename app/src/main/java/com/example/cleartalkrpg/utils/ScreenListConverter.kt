package com.example.cleartalkrpg.utils

import androidx.room.TypeConverter
import com.example.cleartalkrpg.database.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ScreenListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromScreenList(value: MutableList<Screen>): String? {
        val json = gson.toJson(value)
        return json
    }

    @TypeConverter
    fun toScreenList(json: String?): MutableList<Screen> {
        val listType = object : TypeToken<List<Screen>>() {}.type
        val screens: MutableList<Screen> = gson.fromJson(json, listType)
        return screens
    }
}
