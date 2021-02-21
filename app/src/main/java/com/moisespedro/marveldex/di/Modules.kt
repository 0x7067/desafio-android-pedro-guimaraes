package com.moisespedro.marveldex.di

import com.moisespedro.marveldex.api.MarvelClientImpl
import com.moisespedro.marveldex.data.network.ResponseHandlerImpl
import com.moisespedro.marveldex.ui.features.heroMostExpensiveComic.HeroMostExpensiveComicViewModel
import com.moisespedro.marveldex.ui.features.characterlist.ListCharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val responseHandlerModule = module {
    single { ResponseHandlerImpl() }
    single { MarvelClientImpl() }
}

val viewModelModule = module {
    viewModel {
        ListCharactersViewModel(get(), get())
    }
    viewModel {
        HeroMostExpensiveComicViewModel(
            get(),
            get()
        )
    }
}