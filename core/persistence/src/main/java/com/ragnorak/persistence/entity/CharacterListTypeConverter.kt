package com.ragnorak.persistence.entity

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class CharacterListTypeConverter {

    @TypeConverter
    fun fromCharacterList(value: List<CharacterEntity>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toCharacterList(value: String): List<CharacterEntity> {
        return Json.decodeFromString(value)
    }
}