package com.example.marveldex.api

import com.example.marveldex.data.MarvelHeroResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("characters")
    fun getMarvelHeroesAsync(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<MarvelHeroResponse>
}