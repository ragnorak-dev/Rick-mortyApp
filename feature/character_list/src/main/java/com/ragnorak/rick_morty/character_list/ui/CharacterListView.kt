package com.ragnorak.rick_morty.character_list.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ragnorak.rick_morty.character_list.domain.model.CharacterModel
import com.ragnorak.ui.R
import com.ragnorak.ui.ViewState
import com.ragnorak.ui.component.ErrorComponent
import com.ragnorak.ui.component.LoadingComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel()
) {

    val uiState by viewModel.characterListState.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.getCharacters() }
    ) {
        when (val state = uiState) {
            is ViewState.Idle -> {}
            is ViewState.Loading -> {
                isRefreshing = true
                LoadingComponent()
            }

            is ViewState.Success -> {
                isRefreshing = false
                CharacterListView(state.data)
            }

            is ViewState.Error -> {
                isRefreshing = false
                ErrorComponent()
            }
        }
    }
}

@Composable
private fun CharacterListView(characters: List<CharacterModel>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.paddingM))
    ) {
        items(characters.size, key = { it }) { index ->
            val character = characters[index]
            CharacterItem(character)
        }
    }
}

@Composable
private fun CharacterItem(character: CharacterModel) {
    Column {
        Text(text = character.name)
    }
}
