package com.ragnorak.rick_morty.character_list.data.mapper

import com.ragnorak.api.response.CharacterDto
import com.ragnorak.api.response.CharacterListDto
import com.ragnorak.persistence.entity.CharacterEntity
import com.ragnorak.persistence.entity.CharacterListEntity
import com.ragnorak.rick_morty.character_list.domain.model.CharacterListModel
import com.ragnorak.rick_morty.character_list.domain.model.CharacterModel
import com.ragnorak.rick_morty.character_list.domain.model.PaginationInfoModel

fun CharacterListDto.toModel(): CharacterListModel {
    return CharacterListModel(
        pagInfo = PaginationInfoModel(
            currentPage = info.next?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull()?.minus(1)
                ?: info.pages,
            next = info.next?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull(),
            prev = info.prev?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull()
        ),
        characterList = results.map {
            CharacterModel(
                id = it.id,
                name = it.name,
                status = it.status,
                species = it.species,
                type = it.type,
                gender = it.gender,
                image = it.image,
            )
        }

    )
}

fun CharacterDto.toEntity(): CharacterEntity =
    CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        image = image,
    )

fun CharacterListDto.toEntity(): CharacterListEntity =
    CharacterListEntity(
        page = info.next?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull()?.minus(1)
            ?: info.pages,
        count = info.count,
        pages = info.pages,
        next = info.next?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull(),
        prev = info.prev?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull(),
        results = results.map { it.toEntity() }
    )

fun CharacterEntity.toModel(): CharacterModel =
    CharacterModel(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        image = image,
    )

fun CharacterListEntity.toModel(): CharacterListModel =
    CharacterListModel(
        pagInfo = PaginationInfoModel(
            currentPage = page,
            next = next,
            prev = prev
        ),
        characterList = results.map { it.toModel() }
    )