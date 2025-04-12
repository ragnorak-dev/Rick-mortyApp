package com.ragnorak.rick_morty.character_details.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ragnorak.navigation.CharacterDetailsDestination

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.characterDetailsRouting(
    sharedTransitionScope: SharedTransitionScope,
) {
    composable<CharacterDetailsDestination> { backStack ->
        val viewModel: CharacterDetailsViewModel = hiltViewModel()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        val characterId = backStack.toRoute<CharacterDetailsDestination>().characterId

        LaunchedEffect(Unit) {
            viewModel.getCharacterDetails(characterId)
        }

        CharacterDetailsScreen(
            uiState = state,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@composable
        )
    }
}