package com.ragnorak.rick_morty.character_list.domain.model

data class CharacterListModel(
    val pagInfo: PaginationInfoModel,
    val characterList: List<CharacterModel>
)

data class PaginationInfoModel(
    val currentPage: Int,
    val next: Int?,
    val prev: Int?
)