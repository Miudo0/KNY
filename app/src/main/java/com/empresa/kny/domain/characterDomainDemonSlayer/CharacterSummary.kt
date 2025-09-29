package com.empresa.kny.domain.characterDomainDemonSlayer

import com.google.gson.annotations.SerializedName

data class CharacterSummary(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("image") val image: String? = null
)