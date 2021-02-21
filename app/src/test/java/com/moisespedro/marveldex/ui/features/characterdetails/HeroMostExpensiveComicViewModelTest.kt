package com.moisespedro.marveldex.ui.features.characterdetails

import com.moisespedro.marveldex.api.MarvelClientImpl
import com.moisespedro.marveldex.data.comics.MarvelComicsResponse
import com.moisespedro.marveldex.data.network.Resource
import com.moisespedro.marveldex.data.network.ResponseHandlerImpl
import com.moisespedro.marveldex.di.responseHandlerModule
import com.moisespedro.marveldex.di.viewModelModule
import com.moisespedro.marveldex.ui.features.heroMostExpensiveComic.HeroMostExpensiveComicViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito

class HeroMostExpensiveComicViewModelTest : AutoCloseKoinTest() {

    private val mockComicsResponse = mock<MarvelComicsResponse>()

    private val mockComicResponseResourceSuccess = mock<Resource<MarvelComicsResponse>>()

    private val mockComicResponseResourceError = mock<Resource<MarvelComicsResponse>>()

    private val heroMostExpensiveComicViewModel: HeroMostExpensiveComicViewModel by inject()

    private val responseHandler: ResponseHandlerImpl by inject()

    private val marvelClient: MarvelClientImpl by inject()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun before() {
        startKoin {
            modules(viewModelModule, responseHandlerModule)
        }

        declareMock<ResponseHandlerImpl> {
            given(handleSuccess(mockComicsResponse)).will { mockComicResponseResourceSuccess }
            given(handleException<MarvelComicsResponse>(any())).will { mockComicResponseResourceError }
        }

        declareMock<MarvelClientImpl> {
            runBlocking {
                given(getComicsByHeroId(any())).will { mockComicsResponse }
            }
        }
    }

    @Test
    fun `when fetching hero by id with success`() =

        runBlocking {
            // given
            val heroId = 35224

            // when
            heroMostExpensiveComicViewModel.fetchHeroComics(heroId)

            // should
            verify(marvelClient, times(1)).getComicsByHeroId(heroId)
            verify(responseHandler, times(1)).handleSuccess(mockComicsResponse)

            Unit
        }

    @Test
    fun `when fetching hero by id with error`() =

        runBlocking {

            // given
            val heroId = 35224
            val exception = Exception("")

            given(marvelClient.getComicsByHeroId(heroId)).willAnswer {
                throw exception
            }

            // when
            heroMostExpensiveComicViewModel.fetchHeroComics(heroId)

            // should
            verify(marvelClient).getComicsByHeroId(heroId)
            verify(responseHandler).handleException<MarvelComicsResponse>(exception)

            Unit
        }
}