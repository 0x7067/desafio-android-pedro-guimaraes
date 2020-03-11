package com.example.marveldex.ui.features.characterlist

import androidx.lifecycle.ViewModel
import com.example.marveldex.api.MarvelClient
import com.example.marveldex.data.MarvelHero

class ListCharactersViewModel() : ViewModel() {

    suspend fun fetchMarvelHeroes() : List<MarvelHero> {
        val heroes = MarvelClient.getMarvelHeroes(20, 0)
        return heroes.heroData.results
    }
}
