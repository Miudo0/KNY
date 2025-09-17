package com.empresa.kny.domain.charactersDomain

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("totalElements") val totalElements: Int? = null,
    @SerializedName("elementsOnPage") val elementsOnPage: Int? = null,
    @SerializedName("currentPage") val currentPage: Int? = null,
    @SerializedName("totalPages") val totalPages: Int? = null,
    @SerializedName("previousPage") val previousPage: String? = null,
    @SerializedName("nextPage") val nextPage: String? = null
)