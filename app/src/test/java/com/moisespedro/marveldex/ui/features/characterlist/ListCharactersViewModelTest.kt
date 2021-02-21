package com.moisespedro.marveldex.ui.features.characterlist

import com.moisespedro.marveldex.api.MarvelClientImpl
import com.moisespedro.marveldex.data.comics.MarvelComicsResponse
import com.moisespedro.marveldex.data.heroes.MarvelHeroResponse
import com.moisespedro.marveldex.data.network.Resource
import com.moisespedro.marveldex.data.network.ResponseHandlerImpl
import com.moisespedro.marveldex.di.responseHandlerModule
import com.moisespedro.marveldex.di.viewModelModule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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

class ListCharactersViewModelTest : AutoCloseKoinTest() {

    private val mockHeroResponse = mock<MarvelHeroResponse>()

    private val mockHeroResponseResourceSuccess = mock<Resource<MarvelHeroResponse>>()

    private val mockHeroResponseResourceError = mock<Resource<MarvelHeroResponse>>()

    private val responseHandler: ResponseHandlerImpl by inject()

    private val marvelClient: MarvelClientImpl by inject()

    private val listCharactersViewModel: ListCharactersViewModel by inject()

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
            given(handleSuccess(mockHeroResponse)).will { mockHeroResponseResourceSuccess }
            given(handleException<MarvelComicsResponse>(any())).will { mockHeroResponseResourceError }
        }

        declareMock<MarvelClientImpl> {
            runBlocking {
                given(getMarvelHeroes(any(), any())).will { mockHeroResponse }
            }
        }
    }

    @Test
    fun `when fetching marvel heroes with success`() =

        runBlocking {
            // given
            val offset = 35

            // when
            listCharactersViewModel.fetchMarvelHeroes(offset)

            // should
            verify(marvelClient).getMarvelHeroes(20, offset)
            verify(responseHandler).handleSuccess(mockHeroResponse)

            Unit
        }

    @Test
    fun `when fetching marvel heroes with error`() =

        runBlocking {

            // given
            val offset = 35
            val exception = Exception("")

            given(marvelClient.getMarvelHeroes(any(), any())).willAnswer {
                throw exception
            }

            // when
            listCharactersViewModel.fetchMarvelHeroes(offset)

            // should
            verify(marvelClient).getMarvelHeroes(20, offset)
            verify(responseHandler).handleException<MarvelHeroResponse>(exception)

            Unit
        }

}