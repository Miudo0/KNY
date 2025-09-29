package com.empresa.kny.domain.characterDomainDemonSlayer

import com.google.gson.annotations.SerializedName

data class CharacterDetail(
    @SerializedName("name") val name: String? = null,
    @SerializedName("gallery") val gallery: List<String>? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("race") val race: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("age") val age: String? = null,
    @SerializedName("height") val height: String? = null,
    @SerializedName("weight") val weight: String? = null,
    @SerializedName("birthday") val birthday: String? = null,
    @SerializedName("hair color") val hairColor: String? = null,
    @SerializedName("eye color ") val eyeColor: String? = null,
    @SerializedName("affiliation") val affiliation: String? = null,
    @SerializedName("occupation") val occupation: String? = null,
    @SerializedName("combat style") val combatStyle: String? = null,
    @SerializedName("partner(s)") val partners: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("relative(s)") val relatives: String? = null,
    @SerializedName("manga debut") val mangaDebut: String? = null,
    @SerializedName("anime debut") val animeDebut: String? = null,
    @SerializedName("japanese va") val japaneseVa: String? = null,
    @SerializedName("english va") val englishVa: String? = null,
    @SerializedName("stage play") val stagePlay: String? = null
)