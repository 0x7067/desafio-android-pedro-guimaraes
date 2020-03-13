package com.moisespedro.marveldex.data.comics

import com.google.gson.annotations.SerializedName

data class MarvelComicsResponse(@SerializedName("data") val heroData: MarvelComicsData)