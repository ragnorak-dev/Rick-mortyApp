package com.ragnorak.rick_morty.character_list.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ragnorak.navigation.CharacterDetailsDestination
import com.ragnorak.navigation.CharacterListDestination

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.characterListRouting(
    sharedTransitionScope: SharedTransitionScope,
    navController: NavController
) {

    composable<CharacterListDestination> {
        val viewModel: CharacterListViewModel = hiltViewModel()
        val uiState by viewModel.characterListState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.onIntent(CharacterListIntent.LoadCharacters)
        }

        CharacterListScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@composable,
            uiState = uiState,
            actions = CharacterListActions(
                onRetry = { viewModel.onIntent(CharacterListIntent.LoadCharacters) },
                onItemClick = { id -> navController.navigate(CharacterDetailsDestination(id)) },
                loadNextPage = { viewModel.onIntent(CharacterListIntent.LoadMoreCharacters) },
                onSearchQueryChange = { viewModel.onIntent(CharacterListIntent.UpdateSearchQuery(it)) },
                onClickSearchQuery = { viewModel.onIntent(CharacterListIntent.SearchRemoteCharacter(it)) }

            )
        )
    }
}