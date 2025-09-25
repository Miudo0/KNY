package com.empresa.kny.ui

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.empresa.kny.domain.GetCharactersUseCase
import com.empresa.kny.domain.charactersDomain.Content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetCharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
): ViewModel() {

    /**
     * Flow de PagingData que contiene la lista paginada de personajes (Content).
     * Este flujo será recogido en la UI con collectAsLazyPagingItems().
     *
     * cachedIn(viewModelScope) hace que los datos paginados se mantengan
     * en caché mientras el ViewModel esté vivo, evitando recargas innecesarias.
     */
    val characters: Flow<PagingData<Content>> =
        getCharactersUseCase()
            .cachedIn(viewModelScope) // Mantiene el flujo en caché mientras el ViewModel viva
}

