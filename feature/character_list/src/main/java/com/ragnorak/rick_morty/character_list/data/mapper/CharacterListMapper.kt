package com.ragnorak.rick_morty.character_list.data.mapper

import com.ragnorak.api.response.CharacterListDto
import com.ragnorak.rick_morty.character_list.domain.model.CharacterListModel
import com.ragnorak.rick_morty.character_list.domain.model.CharacterModel
import com.ragnorak.rick_morty.character_list.domain.model.PaginationInfoModel

fun CharacterListDto.toModel(): CharacterListModel {
    return CharacterListModel(
        pagInfo = PaginationInfoModel(
            next = infoDto.next?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull(),
            prev = infoDto.prev?.substringAfter("page=")?.substringBefore("&")?.toIntOrNull()
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