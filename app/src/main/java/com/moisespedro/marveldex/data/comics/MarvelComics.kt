package com.moisespedro.marveldex.data.comics

import com.moisespedro.marveldex.data.MarvelThumbnail
import com.google.gson.annotations.SerializedName

data class MarvelComics(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String ?,
    @SerializedName("description") val description: String ?,
    @SerializedName("thumbnail") val thumbnail: MarvelThumbnail
)