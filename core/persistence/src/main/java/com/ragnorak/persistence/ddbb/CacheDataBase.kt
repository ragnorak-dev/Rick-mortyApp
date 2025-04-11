package com.ragnorak.persistence.ddbb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ragnorak.persistence.dao.CharacterDao
import com.ragnorak.persistence.entity.CharacterEntity
import com.ragnorak.persistence.entity.CharacterListEntity
import com.ragnorak.persistence.entity.CharacterListTypeConverter

@Database(
    entities = [CharacterEntity::class, CharacterListEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CharacterListTypeConverter::class)
abstract class CacheDataBase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}