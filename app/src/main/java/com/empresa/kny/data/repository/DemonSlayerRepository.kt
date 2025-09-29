package com.empresa.kny.data.repository

import com.empresa.kny.data.network.DemonSlayerApi
import com.empresa.kny.domain.characterDomainDemonSlayer.CharacterSummary
import com.empresa.kny.domain.characterDomainDemonSlayer.CharacterDetail
import javax.inject.Inject

class DemonSlayerRepository @Inject constructor(
    private val demonSlayerApi: DemonSlayerApi
) {
    suspend fun getAllCharacters(): List<CharacterSummary> {
        return demonSlayerApi.getAllCharacters()
    }

    suspend fun getCharacterDetailByName(name: String): CharacterDetail? {
        val result = demonSlayerApi.getCharacterDetail(name)
        return result.firstOrNull()
    }
}