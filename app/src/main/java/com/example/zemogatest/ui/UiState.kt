package com.example.zemogatest.ui

sealed class UiState {
    data class Loading(val isVisible: Boolean) : UiState()

    data class Success<T>(val data: T) : UiState()

    data class Error(val message: String) : UiState()
}