package com.empresa.kny.domain.charactersDomain

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("pagination") val pagination: Pagination? = null,
    @SerializedName("content") val content: List<Content> = emptyList()
)