package com.ragnorak.rick_morty.character_list.ui

sealed class CharacterListIntent {
    data object LoadCharacters : CharacterListIntent()
    data object RefreshingCharacters : CharacterListIntent()
    data object LoadMoreCharacters : CharacterListIntent()
}