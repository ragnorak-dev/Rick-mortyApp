package com.ragnorak.rick_morty.character_list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ragnorak.rick_morty.character_list.domain.model.CharacterModel
import com.ragnorak.ui.R
import com.ragnorak.ui.ViewState
import com.ragnorak.ui.component.ErrorComponent
import com.ragnorak.ui.component.LoadingComponent
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterListScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    uiState: CharacterListUiState,
    onRetry: () -> Unit = {},
    onItemClick: (Int) -> Unit,
    loadNextPage: () -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        state = pullRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            onRetry()
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
                    characters = state.data,
                    onItemClick = onItemClick,
                    loadNextPage = loadNextPage,
                    isPaginating = uiState.isPaginating
                )
                if (uiState.isPaginating) {
                    LoadingComponent()
                }
            }

            is ViewState.Error -> {
                isRefreshing = false
                ErrorComponent(state.message)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CharacterListView(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    characters: List<CharacterModel>,
    onItemClick: (Int) -> Unit,
    loadNextPage: () -> Unit,
    isPaginating: Boolean
) {
    val gridState = rememberLazyGridState()

    LaunchedEffect(gridState) {
        snapshotFlow {
            gridState.layoutInfo
        }.collect {
            val lastVisibleItem = it.visibleItemsInfo.lastOrNull()
            val totalItemsCount = it.totalItemsCount
            if (lastVisibleItem?.index == totalItemsCount - 1 && !isPaginating) {
                loadNextPage()
            }
        }
    }
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.paddingM)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.paddingS)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.paddingS))
    ) {
        items(characters.size, key = { it }) { index ->
            val character = characters[index]
            CharacterItem(
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                character,
                onItemClick
            )
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
                            animatedVisibilityScope = animatedVisibilityScope
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
                    style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
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
                onRetry = {},
                onItemClick = {},
                loadNextPage = {}
            )
        }
    }
}
