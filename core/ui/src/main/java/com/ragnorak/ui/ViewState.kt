package com.ragnorak.ui

sealed class ViewState <out T> {
    data object Idle : ViewState<Nothing>()
    data object Loading : ViewState<Nothing>()
    data class Success<R>(val data: R) : ViewState<R>()
    data class Error(val message: String) : ViewState<Nothing>()
}