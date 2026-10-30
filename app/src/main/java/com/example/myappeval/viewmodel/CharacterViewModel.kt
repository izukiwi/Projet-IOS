package com.example.myappeval.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myappeval.model.Character
import com.example.myappeval.model.CharacterUiState
import com.example.myappeval.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterViewModel : ViewModel() {

    private val _listState =
        MutableStateFlow<CharacterUiState<List<Character>>>(CharacterUiState.Loading)
    val listState: StateFlow<CharacterUiState<List<Character>>> = _listState.asStateFlow()

    private val _detailState =
        MutableStateFlow<CharacterUiState<Character>>(CharacterUiState.Loading)
    val detailState: StateFlow<CharacterUiState<Character>> = _detailState.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _listState.value = CharacterUiState.Loading
            try {
                val response = RetrofitClient.apiService.getCharacters()
                _listState.value = CharacterUiState.Success(response.results)
            } catch (e: Exception) {
                _listState.value = CharacterUiState.Error(
                    "Impossible de charger les personnages. Vérifie ta connexion."
                )
            }
        }
    }

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            _detailState.value = CharacterUiState.Loading
            try {
                val character = RetrofitClient.apiService.getCharacter(id)
                _detailState.value = CharacterUiState.Success(character)
            } catch (e: Exception) {
                _detailState.value = CharacterUiState.Error(
                    "Impossible de charger ce personnage. Vérifie ta connexion."
                )
            }
        }
    }
}