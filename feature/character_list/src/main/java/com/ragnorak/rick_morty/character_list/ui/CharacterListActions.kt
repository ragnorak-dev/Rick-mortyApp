package com.ragnorak.rick_morty.character_list.ui

data class CharacterListActions(
    val onRetry: () -> Unit,
    val onItemClick: (Int) -> Unit,
    val loadNextPage: () -> Unit,
    val onSearchQueryChange: (String) -> Unit,
    val onClickSearchQuery: (String) -> Unit
)
