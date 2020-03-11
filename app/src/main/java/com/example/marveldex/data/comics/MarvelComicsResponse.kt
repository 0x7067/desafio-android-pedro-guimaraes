package com.example.marveldex.data.comics

import com.example.marveldex.data.MarvelResponse
import com.google.gson.annotations.SerializedName

data class MarvelComicsResponse(
    @SerializedName("data") val heroData: MarvelComicsData
): MarvelResponse()