package com.example.marveldex.data.heroes

import com.example.marveldex.data.MarvelResponse
import com.google.gson.annotations.SerializedName

data class MarvelHeroResponse(@SerializedName("data") val heroData: MarvelHeroData): MarvelResponse()