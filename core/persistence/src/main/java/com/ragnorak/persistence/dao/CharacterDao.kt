package com.ragnorak.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ragnorak.persistence.entity.CharacterEntity
import com.ragnorak.persistence.entity.CharacterListEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterList(characterList: CharacterListEntity)

    @Query("SELECT * FROM character_list_entity WHERE page = :page")
    suspend fun getCharacterListByPage(page: Int): CharacterListEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Query("SELECT * FROM character_list WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?
}
