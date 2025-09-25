package com.empresa.kny.domain

import androidx.paging.PagingData
import com.empresa.kny.data.repository.CharactersRepository
import com.empresa.kny.domain.charactersDomain.CharactersResponse
import com.empresa.kny.domain.charactersDomain.Content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
private val repository: CharactersRepository
){
//    suspend operator fun invoke(pageUrl: String? ): CharactersResponse {
//        return withContext(Dispatchers.IO) {
//            repository.getCharacters(pageUrl)
//        }
//    }
operator fun invoke(): Flow<PagingData<Content>> {
    return repository.getCharactersPager()
}

}