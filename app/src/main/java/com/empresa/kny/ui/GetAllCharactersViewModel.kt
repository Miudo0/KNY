package com.empresa.kny.ui


import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empresa.kny.domain.GetAllCharactersUseCase
import com.empresa.kny.domain.characterDomainDemonSlayer.CharacterSummary

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para obtener todos los personajes de la API de Demon Slayer.
 * Utiliza GetAllCharactersUseCase para recuperar los datos.
 */
@HiltViewModel
class GetAllCharactersViewModel @Inject constructor(

    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    // StateFlow privado para almacenar la lista de personajes.
    private val _characters = MutableStateFlow<List<CharacterSummary>>(emptyList())
    // StateFlow público para exponer los personajes a la UI.
    val characters: StateFlow<List<CharacterSummary>> get() = _characters

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    init {
        fetchCharacters()
    }

    /**
     * Función para obtener los personajes desde el UseCase y actualizar el StateFlow.
     */
    private fun fetchCharacters() {
        viewModelScope.launch {
            try {
                _characters.value = getAllCharactersUseCase()
                _error.value = null
            } catch (t: Throwable) {
                _error.value = t.message ?: "Error desconocido"
            }
        }
    }
}