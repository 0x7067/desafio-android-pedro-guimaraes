package com.moisespedro.marveldex.ui.features.characterlist

import androidx.lifecycle.ViewModel
import com.moisespedro.marveldex.api.MarvelClientImpl
import com.moisespedro.marveldex.data.heroes.MarvelHeroResponse
import com.moisespedro.marveldex.data.network.Resource
import com.moisespedro.marveldex.data.network.ResponseHandlerImpl

class ListCharactersViewModel(val responseHandler: ResponseHandlerImpl, val marvelClient: MarvelClientImpl) : ViewModel() {

    suspend fun fetchMarvelHeroes(offset: Int): Resource<MarvelHeroResponse> {
        return try {
            responseHandler.handleSuccess(marvelClient.getMarvelHeroes(20, offset))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}
