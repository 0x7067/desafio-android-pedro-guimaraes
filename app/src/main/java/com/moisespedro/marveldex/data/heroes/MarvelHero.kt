package com.moisespedro.marveldex.data.heroes

import com.google.gson.annotations.SerializedName
import com.moisespedro.marveldex.data.MarvelThumbnail

data class MarvelHero(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnail") val thumbnail: MarvelThumbnail
)