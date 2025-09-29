package com.empresa.kny.data.network

import com.empresa.kny.domain.characterDomainDemonSlayer.CharacterDetail
import com.empresa.kny.domain.characterDomainDemonSlayer.CharacterSummary
import retrofit2.http.GET
import retrofit2.http.Path

interface DemonSlayerApi {
    @GET("v1/")
    suspend fun getAllCharacters(): List<CharacterSummary>

    @GET("v1/{name}")
    suspend fun getCharacterDetail(@Path("name") name: String): List<CharacterDetail>
}