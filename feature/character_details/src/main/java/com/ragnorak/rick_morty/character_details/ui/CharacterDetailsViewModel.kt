package com.ragnorak.rick_morty.character_details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnorak.rick_morty.character_details.domain.model.CharacterDetailsModel
import com.ragnorak.rick_morty.character_details.domain.repository.CharacterDetailsRepository
import com.ragnorak.ui.ViewState
import com.ragnorak.ui.errormanage.mapToUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: CharacterDetailsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ViewState<CharacterDetailsModel>>(ViewState.Idle)
    val uiState = _uiState.asStateFlow()

    fun getCharacterDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ViewState.Loading
            repository.getCharacterDetails(id)
                .onSuccess {
                    _uiState.value = ViewState.Success(it)
                }
                .onFailure {
                    _uiState.value = ViewState.Error(it.mapToUiError { getCharacterDetails(id) })
                }
        }
    }
}