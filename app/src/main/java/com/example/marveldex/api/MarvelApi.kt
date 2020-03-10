package com.example.marveldex.api

import com.example.marveldex.data.MarvelHeroResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getMarvelHeroes(@Query("limit") limit: Int, @Query("offset") offset: Int): MarvelHeroResponse
}