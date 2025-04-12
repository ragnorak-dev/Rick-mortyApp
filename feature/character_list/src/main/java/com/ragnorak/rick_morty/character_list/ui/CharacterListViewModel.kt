package com.ragnorak.rick_morty.character_list.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnorak.rick_morty.character_list.domain.model.CharacterModel
import com.ragnorak.rick_morty.character_list.domain.repository.CharacterListRepository
import com.ragnorak.ui.ViewState
import com.ragnorak.ui.errormanage.mapToUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterListRepository: CharacterListRepository
) : ViewModel() {

    private val _characterListState = MutableStateFlow(CharacterListUiState())
    val characterListState = _characterListState.asStateFlow()

    private var currentPage = 0
    private var canPaginate = true
    private val characterList = mutableStateListOf<CharacterModel>()

    fun onIntent(intent: CharacterListIntent) {
        when (intent) {
            CharacterListIntent.LoadCharacters -> {
                getCharacters()
            }
            CharacterListIntent.RefreshingCharacters -> {
                refreshList()
            }
            CharacterListIntent.LoadMoreCharacters -> {
                loadMoreCharacters()
            }
            is CharacterListIntent.UpdateSearchQuery -> {
                filterLocalQuery(intent.query)
            }
            is CharacterListIntent.SearchRemoteCharacter -> {
                searchRemoteQuery(intent.query)
            }
        }
    }

    private fun refreshList() {
        canPaginate = true
        _characterListState.value = _characterListState.value.copy(isRefreshing = true)
        getCharacters(isRefreshing = true, page = 0)
    }

    private fun loadMoreCharacters() {
        if (!_characterListState.value.isPaginating && canPaginate) {
            currentPage++
            _characterListState.value = _characterListState.value.copy(isPaginating = true)
            getCharacters(page = currentPage)
        }
    }

    private fun filterLocalQuery(query: String) {
        canPaginate = true
        val filtered = characterList.toList().filter {
            it.name.contains(query, ignoreCase = true)
        }
        _characterListState.value = _characterListState.value.copy(
            searchQuery = query,
            listState = ViewState.Success(filtered),
            filteredList = filtered
        )
    }

    private fun searchRemoteQuery(query: String) {
        viewModelScope.launch {
            _characterListState.value =
                _characterListState.value.copy(listState = ViewState.Loading)
            characterListRepository.getCharactersByName(query).onSuccess {
                canPaginate = false
                _characterListState.value = _characterListState.value.copy(
                    searchQuery = query,
                    listState = ViewState.Success(it.characterList),
                    isRefreshing = false,
                    isPaginating = false
                )
            }
                .onFailure {
                    _characterListState.value = _characterListState.value.copy(
                        listState = ViewState.Error(it.mapToUiError { searchRemoteQuery(query) }),
                        isRefreshing = false,
                        isPaginating = false
                    )
                }
        }
    }

    private fun getCharacters(isRefreshing: Boolean = false, page: Int = currentPage) {
        viewModelScope.launch {
            if (!isRefreshing && page == 0) {
                _characterListState.value =
                    _characterListState.value.copy(listState = ViewState.Loading)
            }

            characterListRepository.getCharacters(page)
                .onSuccess {
                    currentPage = it.pagInfo.currentPage
                    canPaginate = it.pagInfo.next != null
                    if (page == 0 || isRefreshing) {
                        characterList.clear()
                    }
                    characterList.addAll(it.characterList)
                    _characterListState.value = _characterListState.value.copy(
                        listState = ViewState.Success(characterList.toList()),
                        isRefreshing = false,
                        isPaginating = false
                    )
                }
                .onFailure {
                    _characterListState.value = _characterListState.value.copy(
                        listState = ViewState.Error(it.mapToUiError { getCharacters(isRefreshing, page) }),
                        isRefreshing = false,
                        isPaginating = false
                    )
                }
        }
    }
}