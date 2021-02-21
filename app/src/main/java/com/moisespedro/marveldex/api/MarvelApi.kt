package com.moisespedro.marveldex.api

import com.moisespedro.marveldex.data.comics.MarvelComicsResponse
import com.moisespedro.marveldex.data.heroes.MarvelHeroResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getMarvelHeroes(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): MarvelHeroResponse

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getComicsByHero(@Path("characterId") characterId: Int): MarvelComicsResponse
}