package com.empresa.kny.domain

import com.empresa.kny.domain.characterDomainDemonSlayer.CharacterSummary


import com.empresa.kny.data.repository.DemonSlayerRepository
import javax.inject.Inject

/**
 * UseCase para obtener todos los personajes de la API de Demon Slayer.
 * Este caso de uso encapsula la lógica de negocio y llama al repositorio correspondiente.
 */
class GetAllCharactersUseCase @Inject constructor(
    private val demonSlayerRepository: DemonSlayerRepository
) {
    suspend operator fun invoke(): List<CharacterSummary> {
        return demonSlayerRepository.getAllCharacters()
    }
}