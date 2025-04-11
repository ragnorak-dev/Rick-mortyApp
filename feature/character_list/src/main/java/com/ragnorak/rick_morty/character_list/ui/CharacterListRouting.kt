package com.ragnorak.rick_morty.character_list.ui

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ragnorak.navigation.CharacterDetailsDestination
import com.ragnorak.navigation.CharacterListDestination

fun NavGraphBuilder.characterListRouting(navController: NavController) {
    composable<CharacterListDestination> {
        val viewModel: CharacterListViewModel = hiltViewModel()
        val uiState by viewModel.characterListState.collectAsStateWithLifecycle()

        CharacterListScreen(
            uiState = uiState,
            onRetry = { viewModel.getCharacters() },
            onItemClick = { id -> navController.navigate(CharacterDetailsDestination(id)) }
        )
    }
}