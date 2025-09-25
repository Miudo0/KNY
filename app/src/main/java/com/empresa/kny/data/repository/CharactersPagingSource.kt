package com.empresa.kny.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.empresa.kny.data.network.KNYApiCharacters
import com.empresa.kny.domain.charactersDomain.Content

class CharactersPagingSource    ( private val api: KNYApiCharacters
) : PagingSource<String, Content>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Content> {
        return try {
            val key = params.key
            val response = if (key == null) {
                // primera página
                api.getCharacters()
            } else {
                // siguientes páginas usando la URL completa
                api.getCharacters(key)
            }

            LoadResult.Page(
                data = response.content,
                prevKey = response.pagination?.previousPage,
                nextKey = response.pagination?.nextPage
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Content>): String? {
        return state.anchorPosition?.let { anchorPos ->
            val anchorPage = state.closestPageToPosition(anchorPos)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }
}