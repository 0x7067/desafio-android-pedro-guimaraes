package com.moisespedro.marveldex.ui.features.heroMostExpensiveComic

import androidx.lifecycle.ViewModel
import com.moisespedro.marveldex.api.MarvelClientImpl
import com.moisespedro.marveldex.data.comics.MarvelComicsResponse
import com.moisespedro.marveldex.data.network.Resource
import com.moisespedro.marveldex.data.network.ResponseHandlerImpl

class HeroMostExpensiveComicViewModel(private val responseHandler: ResponseHandlerImpl, private val marvelClient: MarvelClientImpl) : ViewModel() {

    suspend fun fetchHeroComics(heroID: Int): Resource<MarvelComicsResponse> {
        return try {
            responseHandler.handleSuccess(marvelClient.getComicsByHeroId(heroID))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}