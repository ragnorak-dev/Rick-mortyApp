package com.ragnorak.rick_morty.character_details.data.datasource

import com.ragnorak.persistence.dao.CharacterDao
import com.ragnorak.persistence.entity.CharacterEntity
import javax.inject.Inject

class CharacterDetailsLocalDataSource @Inject constructor(
    private val characterDao: CharacterDao
) {
    suspend fun getCharacterDetails(characterId: Int): Result<CharacterEntity> =
        try {
            val character = characterDao.getCharacterById(id = characterId)
            character?.let { Result.success(it) } ?: Result.failure(Exception("Character not found"))
        } catch (exception: Exception) {
            Result.failure(exception)
        }

    suspend fun saveCharacterDetails(character: CharacterEntity) =
        try {
            characterDao.insertCharacter(character)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
}