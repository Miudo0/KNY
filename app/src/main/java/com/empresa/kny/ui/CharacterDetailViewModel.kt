package com.empresa.kny.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empresa.kny.domain.GetCharacterDetailUseCase
import com.empresa.kny.domain.characterDomainDemonSlayer.CharacterDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetail: GetCharacterDetailUseCase
) : ViewModel() {

    private val _detail = MutableStateFlow<CharacterDetail?>(null)
    val detail: StateFlow<CharacterDetail?> get() = _detail

    fun load(name: String) {
        viewModelScope.launch {
            _detail.value = getCharacterDetail(name)
        }
    }
}


