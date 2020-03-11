package com.example.marveldex.ui.features.characterdetails

import androidx.lifecycle.ViewModel
import com.example.marveldex.api.MarvelClient
import com.example.marveldex.data.comics.MarvelComics

class CharacterDetailsViewModel() : ViewModel() {

    suspend fun fetchHeroComics(heroID: Int): List<MarvelComics> {
        val heroComics = MarvelClient.getComicsByHeroId(heroID)
        return heroComics.heroData.results
    }
}