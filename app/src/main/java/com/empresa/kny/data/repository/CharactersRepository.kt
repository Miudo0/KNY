package com.empresa.kny.data.repository

import com.empresa.kny.data.network.KNYApiCharacters
import com.empresa.kny.domain.charactersDomain.CharactersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val kNYApiCharacters: KNYApiCharacters
) {

    suspend fun getCharacters(): CharactersResponse {
        return withContext(Dispatchers.IO) {
            kNYApiCharacters.getCharacters()
        }
    }
}
