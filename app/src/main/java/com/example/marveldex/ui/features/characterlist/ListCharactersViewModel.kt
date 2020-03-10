package com.example.marveldex.ui.features.characterlist

import androidx.lifecycle.ViewModel
import com.example.marveldex.api.MarvelClient

class ListCharactersViewModel() : ViewModel() {

    suspend fun fetchMarvelHeroes() {
        val heroes = MarvelClient.getMarvelHeroes(20, 0)
        print(heroes.heroData.count)
    }
}
