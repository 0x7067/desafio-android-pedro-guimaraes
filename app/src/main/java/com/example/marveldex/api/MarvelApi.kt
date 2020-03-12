package com.example.marveldex.api

import com.example.marveldex.data.comics.MarvelComicsResponse
import com.example.marveldex.data.heroes.MarvelHeroResponse
import com.example.marveldex.data.network.Resource
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getMarvelHeroes(@Query("limit") limit: Int, @Query("offset") offset: Int): MarvelHeroResponse

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComicsByHero(@Path("characterId") characterId: Int): MarvelComicsResponse
}