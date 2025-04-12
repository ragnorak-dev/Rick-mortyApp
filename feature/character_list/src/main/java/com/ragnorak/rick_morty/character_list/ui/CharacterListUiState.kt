package com.ragnorak.rick_morty.character_list.ui

import com.ragnorak.rick_morty.character_list.domain.model.CharacterModel
import com.ragnorak.ui.ViewState

data class CharacterListUiState(
    val listState: ViewState<List<CharacterModel>> = ViewState.Idle,
    val isPaginating: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val filteredList: List<CharacterModel> = emptyList()
)
