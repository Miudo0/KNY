package com.empresa.kny.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.empresa.kny.data.network.KNYApiCharacters
import com.empresa.kny.domain.charactersDomain.CharactersResponse
import com.empresa.kny.domain.charactersDomain.Content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val kNYApiCharacters: KNYApiCharacters
) {

    suspend fun getCharacters(pageUrl: String? ): CharactersResponse {
        return withContext(Dispatchers.IO) {
            if(pageUrl == null) {
                kNYApiCharacters.getCharacters()
            }else{
                kNYApiCharacters.getCharacters(pageUrl)
            }
        }
    }
    // --- NUEVO: Pager para Paging 3 ---
    fun getCharactersPager(): Flow<PagingData<Content>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,            // coincide con tu API
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharactersPagingSource(kNYApiCharacters) }
        ).flow
    }
}
