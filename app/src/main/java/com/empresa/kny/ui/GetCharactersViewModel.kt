package com.empresa.kny.ui

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empresa.kny.domain.GetCharactersUseCase
import com.empresa.kny.domain.charactersDomain.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetCharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
): ViewModel() {
    private val _characters = MutableStateFlow<CharactersState>(CharactersState.Loading)
    val characters: StateFlow<CharactersState> = _characters

    fun getCharacters() {
        viewModelScope.launch {
            try {
                Log.d("GetCharactersViewModel", "Calling getCharactersUseCase API")
                val response = getCharactersUseCase()
                Log.d("GetCharactersViewModel", "Full response: $response")
                Log.d("GetCharactersViewModel", "Response content: ${response.content}")
                _characters.value = CharactersState.Success(response.content)
            } catch (e: Exception) {
                Log.e("GetCharactersViewModel", "Error fetching characters", e)
                _characters.value = CharactersState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed interface CharactersState {
    object Loading : CharactersState
    data class Success(val characters: List<Content>) : CharactersState
    data class Error(val message: String) : CharactersState
}