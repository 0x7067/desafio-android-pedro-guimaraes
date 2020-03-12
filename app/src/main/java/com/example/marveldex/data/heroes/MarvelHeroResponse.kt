package com.example.marveldex.data.heroes

import com.google.gson.annotations.SerializedName

data class MarvelHeroResponse(@SerializedName("data") val heroData: MarvelHeroData)