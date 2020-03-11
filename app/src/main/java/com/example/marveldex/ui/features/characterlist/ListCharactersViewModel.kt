package com.example.marveldex.ui.features.characterlist

import androidx.lifecycle.ViewModel
import com.example.marveldex.api.MarvelClient
import com.example.marveldex.data.heroes.MarvelHero

class ListCharactersViewModel() : ViewModel() {

    suspend fun fetchMarvelHeroes(offset: Int) : List<MarvelHero> {
        val heroes = MarvelClient.getMarvelHeroes(20, offset)
        return heroes.heroData.results
    }
}
