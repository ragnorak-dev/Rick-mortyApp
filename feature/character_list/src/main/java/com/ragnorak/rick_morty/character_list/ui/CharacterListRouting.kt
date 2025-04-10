package com.ragnorak.rick_morty.character_list.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ragnorak.navigation.CharacterListDestination

fun NavGraphBuilder.characterListRouting() {
    composable<CharacterListDestination> {
        CharacterListScreen()
    }
}