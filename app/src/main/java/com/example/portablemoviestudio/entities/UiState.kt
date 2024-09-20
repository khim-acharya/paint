package com.example.portablemoviestudio.entities

sealed  class UiState<out T> {
    object Loading: UiState<Nothing>()
    data class Success<out T>(val res: T): UiState<T>()
    data class Error<out T>(val message: String): UiState<T>()
}
