package com.empresa.kny.data.network

import com.empresa.kny.domain.charactersDomain.CharactersResponse
import retrofit2.http.GET

interface KNYApiCharacters {
    @GET("characters")
    suspend fun getCharacters() : CharactersResponse
}