package com.moisespedro.marveldex.data.comics

import com.google.gson.annotations.SerializedName
import com.moisespedro.marveldex.data.MarvelThumbnail

data class MarvelComics(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: MarvelThumbnail,
    @SerializedName("prices") val prices: List<MarvelComicPrice>
) {
    data class MarvelComicPrice(
        @SerializedName("price")
        val price: Double,
        @SerializedName("type")
        val type: String
    )

    fun getComicMostExpensivePrice(): Double {
        var biggestPrice = 0.0

        prices.forEach {
            if (it.price > biggestPrice) {
                biggestPrice = it.price
            }
        }
        return biggestPrice
    }
}