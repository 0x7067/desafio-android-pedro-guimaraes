package com.moisespedro.marveldex.api

import com.moisespedro.marveldex.data.comics.MarvelComicsResponse
import com.moisespedro.marveldex.data.heroes.MarvelHeroResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MarvelClient {
    @Throws(Exception::class)
    suspend fun getMarvelHeroes(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): MarvelHeroResponse

    @Throws(Exception::class)
    suspend fun getComicsByHeroId(characterId: Int): MarvelComicsResponse
}

class MarvelClientImpl : MarvelClient {

    private val baseUrl = "https://gateway.marvel.com"

    private fun getMarvelApi(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient() =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(MarvelApiInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()

    override suspend fun getMarvelHeroes(@Query("limit") limit: Int, @Query("offset") offset: Int) =
        getMarvelApi(getRetrofit()).getMarvelHeroes(limit, offset)

    override suspend fun getComicsByHeroId(characterId: Int) =
        getMarvelApi(getRetrofit()).getComicsByHero(characterId)
}