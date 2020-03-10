package com.example.marveldex.data

import com.google.gson.annotations.SerializedName

data class MarvelHeroResponse(@SerializedName("data") val heroData: MarvelHeroData): MarvelResponse()