package com.ragnorak.rick_morty.character_list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnorak.rick_morty.character_list.domain.model.CharacterModel
import com.ragnorak.rick_morty.character_list.domain.repository.CharacterListRepository
import com.ragnorak.ui.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterListRepository: CharacterListRepository
): ViewModel() {

    private val _characterListState = MutableStateFlow<ViewState<List<CharacterModel>>>(ViewState.Idle)
    val characterListState = _characterListState.asStateFlow()
        .onStart { getCharacters() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewState.Idle)

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            _characterListState.value = ViewState.Loading
            characterListRepository.getCharacters()
                .onSuccess {
                    _characterListState.value = ViewState.Success(it.characterList)
                }
                .onFailure {
                    _characterListState.value = ViewState.Error(it.message ?: "Unknown error")
                }
        }
    }
}