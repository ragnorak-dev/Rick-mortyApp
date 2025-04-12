package com.ragnorak.rick_morty.character_list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.ragnorak.rick_morty.character_list.domain.model.CharacterModel
import com.ragnorak.rick_morty.character_list.ui.component.SearchBar
import com.ragnorak.ui.R
import com.ragnorak.ui.ViewState
import com.ragnorak.ui.component.ErrorComponent
import com.ragnorak.ui.component.LoadingComponent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterListScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: CharacterListUiState,
    actions: CharacterListActions
) {
    val pullRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            actions.onRetry()
        }
    ) {
        when (val state = uiState.listState) {
            is ViewState.Idle -> {}
            is ViewState.Loading -> {
                if (!uiState.isRefreshing && !uiState.isPaginating) {
                    LoadingComponent()
                }
            }

            is ViewState.Success -> {
                isRefreshing = false
                CharacterListView(
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    uiState = uiState,
                    actions = actions
                )

                if (uiState.isPaginating) {
                    LoadingComponent()
                }
            }

            is ViewState.Error -> {
                isRefreshing = false
                ErrorComponent(state.uiError)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CharacterListView(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: CharacterListUiState,
    actions: CharacterListActions
) {
    val gridState = rememberLazyGridState()
    val isExiting = remember { mutableStateOf(true) }
    val characters = if (uiState.searchQuery.isEmpty()) {
        (uiState.listState as ViewState.Success<List<CharacterModel>>).data
    } else {
        uiState.filteredList
    }

    LaunchedEffect(gridState) {
        snapshotFlow {
            gridState.layoutInfo
        }.collect {
            val lastVisibleItem = it.visibleItemsInfo.lastOrNull()
            val totalItemsCount = it.totalItemsCount
            if (lastVisibleItem?.index == totalItemsCount - 1 && !uiState.isPaginating) {
                actions.loadNextPage()
            }
        }
    }
    LaunchedEffect(uiState.listState) {
        if (uiState.listState is ViewState.Success) {
            isExiting.value = true
        }
    }

    Scaffold(modifier = Modifier.imePadding(),
        topBar = {
            with(sharedTransitionScope) {
                Column(
                    modifier = Modifier.renderInSharedTransitionScopeOverlay(
                        renderInOverlay = { isExiting.value }
                    )
                ) {
                    SearchBar(
                        uiState = uiState,
                        actions = actions
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.paddingXS)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.paddingXS)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.paddingXS))
            ) {
                items(characters.size, key = { it }) { index ->
                    val character = characters[index]
                    CharacterItem(
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        character = character,
                        onItemClick =
                            {
                                isExiting.value = false
                                actions.onItemClick(it)
                            }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CharacterItem(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    character: CharacterModel,
    onItemClick: (Int) -> Unit
) {
    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .clickable { onItemClick(character.id) }
                .fillMaxSize(),
        ) {
            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.paddingS))) {
                AsyncImage(
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "image-${character.image}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium),
                    model = character.image,
                    contentDescription = character.name,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop

                )
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.paddingXS))
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun CharacterDetailsScreenPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(visible = true) {
            CharacterListScreen(
                sharedTransitionScope = this@SharedTransitionLayout,
                animatedVisibilityScope = this,
                uiState = CharacterListUiState(
                    listState = ViewState.Success(
                        listOf(
                            CharacterModel(
                                id = 1,
                                name = "Rick Sanchez",
                                status = "Alive",
                                species = "Human",
                                type = "",
                                gender = "Male",
                                image = ""
                            ),
                            CharacterModel(
                                id = 1,
                                name = "Rick Sanchez",
                                status = "Alive",
                                species = "Human",
                                type = "",
                                gender = "Male",
                                image = ""
                            ),
                            CharacterModel(
                                id = 1,
                                name = "Rick Sanchez",
                                status = "Alive",
                                species = "Human",
                                type = "",
                                gender = "Male",
                                image = ""
                            )
                        )
                    ),
                    isRefreshing = false,
                    isPaginating = false
                ),
                actions = CharacterListActions(
                    onRetry = {},
                    onItemClick = {},
                    loadNextPage = {},
                    onSearchQueryChange = {},
                    onClickSearchQuery = {}
                )
            )
        }
    }
}
