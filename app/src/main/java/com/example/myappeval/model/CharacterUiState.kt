package com.example.myappeval.model

sealed class CharacterUiState<out T> {
    object Loading : CharacterUiState<Nothing>()
    data class Success<T>(val data: T) : CharacterUiState<T>()
    data class Error(val message: String) : CharacterUiState<Nothing>()
}