package com.ragnorak.rick_morty.character_list.data.datasource

import com.ragnorak.persistence.dao.CharacterDao
import com.ragnorak.persistence.entity.CharacterListEntity
import javax.inject.Inject

class CharacterListLocalDataSource @Inject constructor(
    private val dao: CharacterDao
) {

    suspend fun getCharactersByPage(page: Int): Result<CharacterListEntity> =
        try {
            dao.getCharacterListByPage(page)?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Character list not found"))

        } catch (exception: Exception) {
            Result.failure(exception)
        }

    suspend fun saveCharactersPage(charactersPage: CharacterListEntity) =
        try {
            Result.success(dao.insertCharacterList(charactersPage))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
}