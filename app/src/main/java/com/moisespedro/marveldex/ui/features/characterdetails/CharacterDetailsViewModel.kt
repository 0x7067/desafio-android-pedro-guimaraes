package com.moisespedro.marveldex.ui.features.characterdetails

import androidx.lifecycle.ViewModel
import com.moisespedro.marveldex.api.MarvelClient
import com.moisespedro.marveldex.data.comics.MarvelComicsResponse
import com.moisespedro.marveldex.data.network.Resource
import com.moisespedro.marveldex.data.network.ResponseHandler

class CharacterDetailsViewModel() : ViewModel() {

    lateinit var responseHandler: ResponseHandler
    lateinit var marvelClient: MarvelClient

    suspend fun fetchHeroComics(heroID: Int): Resource<MarvelComicsResponse> {
        return try {
            responseHandler.handleSuccess(marvelClient.getComicsByHeroId(heroID))
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}