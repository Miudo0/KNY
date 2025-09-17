package com.empresa.kny.domain.charactersDomain

import com.google.gson.annotations.SerializedName

class Content {
    @SerializedName("id"             ) val id            : Int?    = null
    @SerializedName("name"           ) val name          : String? = null
    @SerializedName("age"            ) val age           : Int?    = null
    @SerializedName("gender"         ) val gender        : String? = null
    @SerializedName("race"           ) val race          : String? = null
    @SerializedName("description"    ) val description   : String? = null
    @SerializedName("img"            ) val img           : String? = null
    @SerializedName("affiliation_id" ) val affiliationId : Int?    = null
    @SerializedName("arc_id"         ) val arcId         : Int?    = null
    @SerializedName("quote"          ) val quote         : String? = null
}