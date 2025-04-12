package com.ragnorak.ui

import com.ragnorak.ui.errormanage.UiError

sealed class ViewState <out T> {
    data object Idle : ViewState<Nothing>()
    data object Loading : ViewState<Nothing>()
    data class Success<R>(val data: R) : ViewState<R>()
    data class Error(val uiError: UiError) : ViewState<Nothing>()
}