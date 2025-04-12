package com.ragnorak.rick_morty.character_list.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ragnorak.rick_morty.character_list.ui.CharacterListActions
import com.ragnorak.rick_morty.character_list.ui.CharacterListUiState
import com.ragnorak.ui.R

@Composable
fun SearchBar(
    uiState: CharacterListUiState,
    actions: CharacterListActions,
) {
    TextField(
        value = uiState.searchQuery,
        onValueChange = actions.onSearchQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = stringResource(id = R.string.character_search_bar_placeholder)) },
        leadingIcon = {
            Icon(
                modifier = Modifier.clickable { actions.onClickSearchQuery(uiState.searchQuery) },
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.character_search_bar_placeholder)
            )
        },
        trailingIcon = {
            if (uiState.searchQuery.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable { actions.onSearchQueryChange("") },
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.character_search_bar_placeholder)
                )
            }
        }
    )
}