package com.example.marveldex.data.heroes

import com.example.marveldex.data.MarvelThumbnail
import com.google.gson.annotations.SerializedName

data class MarvelHero(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("thumbnail") val thumbnail: MarvelThumbnail
)