package com.empresa.kny.domain

import com.empresa.kny.data.repository.DemonSlayerRepository
import com.empresa.kny.domain.characterDomainDemonSlayer.CharacterDetail
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val repository: DemonSlayerRepository
) {
    suspend operator fun invoke(name: String): CharacterDetail? {
        return repository.getCharacterDetailByName(name)
    }
}


