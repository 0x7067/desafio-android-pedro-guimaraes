package com.example.marveldex.data

import com.google.gson.annotations.SerializedName

data class MarvelThumbnail(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
) {
    fun getUrl() : String {
        return path.replace("http", "https") + "." + extension
    }
}