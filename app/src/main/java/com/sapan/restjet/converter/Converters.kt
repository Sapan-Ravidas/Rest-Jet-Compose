package com.sapan.restjet.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun mapToString(map: Map<String, String>?): String =
        gson.toJson(map ?: emptyMap<String, String>())

    @TypeConverter
    fun stringToMap(string: String?): Map<String, String> =
        if (string.isNullOrEmpty()) {
            emptyMap()
        } else {
            gson.fromJson(string, object : TypeToken<Map<String, String>>() {}.type)
        }

    @TypeConverter
    fun stringListToString(list: List<String>?): String =
        gson.toJson(list ?: emptyList<String>())

    @TypeConverter
    fun stringToListOfString(string: String?): List<String> =
        if (string.isNullOrEmpty()) {
            emptyList()
        } else {
            gson.fromJson(string, object: TypeToken<List<String>>(){}.type)
        }
}