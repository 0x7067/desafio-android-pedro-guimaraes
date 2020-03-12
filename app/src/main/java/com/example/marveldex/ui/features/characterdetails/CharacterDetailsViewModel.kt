package com.example.marveldex.ui.features.characterdetails

import androidx.lifecycle.ViewModel
import com.example.marveldex.api.MarvelClient
import com.example.marveldex.data.comics.MarvelComics
import com.example.marveldex.data.comics.MarvelComicsResponse
import com.example.marveldex.data.network.Resource
import com.example.marveldex.data.network.ResponseHandler

class CharacterDetailsViewModel() : ViewModel() {

    suspend fun fetchHeroComics(heroID: Int): Resource<MarvelComicsResponse> {
        return try {
            ResponseHandler.handleSuccess(MarvelClient.getComicsByHeroId(heroID))
        } catch (e: Exception) {
            ResponseHandler.handleException(e)
        }
    }
}