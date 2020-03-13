package com.example.marveldex.ui.features.characterdetails

import androidx.lifecycle.ViewModel
import com.example.marveldex.api.MarvelClient
import com.example.marveldex.data.comics.MarvelComics
import com.example.marveldex.data.comics.MarvelComicsResponse
import com.example.marveldex.data.network.Resource
import com.example.marveldex.data.network.ResponseHandler

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