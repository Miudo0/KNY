package com.empresa.kny.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.empresa.kny.domain.GetCharactersUseCase
import com.empresa.kny.domain.charactersDomain.Content
import com.empresa.kny.domain.translation.TranslateTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TranslationState {
    object Loading : TranslationState
    data class Success(val text: String) : TranslationState
}

@HiltViewModel
class GetCharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val translateTextUseCase: TranslateTextUseCase
) : ViewModel() {

    val characters: Flow<PagingData<Content>> =
        getCharactersUseCase()
            .cachedIn(viewModelScope)

    // Mapa que guarda el estado de traducción para cada clave única
    private val _translations = mutableMapOf<String, MutableStateFlow<TranslationState>>()

    /**
     * Obtiene el estado de traducción para una clave única.
     * Si no existe, crea uno nuevo con estado Loading.
     */
    fun getTranslationState(key: String): StateFlow<TranslationState> {
        return _translations.getOrPut(key) { MutableStateFlow(TranslationState.Loading) }
    }

    /**
     * Traduce un texto identificado por una clave única.
     */
    fun translate(context: Context, key: String, text: String) {
        val stateFlow = _translations.getOrPut(key) { MutableStateFlow(TranslationState.Loading) }
        viewModelScope.launch {
            stateFlow.value = TranslationState.Loading
            try {
                val translated = translateTextUseCase.execute(context, text)
                stateFlow.value = TranslationState.Success(translated)
            } catch (e: Exception) {
                stateFlow.value = TranslationState.Success(text) // fallback a texto original
            }
        }
    }
}