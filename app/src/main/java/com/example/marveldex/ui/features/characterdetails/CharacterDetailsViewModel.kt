package com.example.marveldex.ui.features.characterdetails

import androidx.lifecycle.ViewModel
import com.example.marveldex.api.MarvelClient

class CharacterDetailsViewModel() : ViewModel() {

    suspend fun fetchHeroComics(heroID: Int) {
        val heroComics = MarvelClient.getComicsByHeroId(heroID)
    }
}