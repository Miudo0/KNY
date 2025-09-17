package com.empresa.kny.domain

import com.empresa.kny.data.repository.CharactersRepository
import com.empresa.kny.domain.charactersDomain.CharactersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
private val repository: CharactersRepository
){
    suspend operator fun invoke(): CharactersResponse {
        return withContext(Dispatchers.IO) {
            repository.getCharacters()
        }
    }
}