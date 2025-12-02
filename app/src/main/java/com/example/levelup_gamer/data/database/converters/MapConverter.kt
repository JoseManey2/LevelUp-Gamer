package com.example.levelup_gamer.data.database.converters

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MapConverter {
    @TypeConverter
    fun fromString(value: String): Map<String, Int> {
        if (value.isEmpty()) return emptyMap()
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromMap(map: Map<String, Int>): String {
        return Json.encodeToString(map)
    }
}
