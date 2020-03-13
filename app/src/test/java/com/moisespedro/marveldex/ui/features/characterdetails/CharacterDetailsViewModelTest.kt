package com.moisespedro.marveldex.ui.features.characterdetails

import com.moisespedro.marveldex.api.MarvelClient
import com.moisespedro.marveldex.data.comics.MarvelComicsResponse
import com.moisespedro.marveldex.data.network.Resource
import com.moisespedro.marveldex.data.network.ResponseHandler
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class CharacterDetailsViewModelTest {

    private val mockComicsResponse = mock<MarvelComicsResponse>()

    private val mockComicResponseResourceSuccess = mock<Resource<MarvelComicsResponse>>()

    private val mockComicResponseResourceError = mock<Resource<MarvelComicsResponse>>()

    private val marvelClientMock = mock<MarvelClient> {
        onBlocking { getComicsByHeroId(any()) } doReturn mockComicsResponse
    }

    private val responseHandlerMock = mock<ResponseHandler> {
        on { handleSuccess(mockComicsResponse) } doReturn mockComicResponseResourceSuccess
        on { handleException<MarvelComicsResponse>(any()) } doReturn mockComicResponseResourceError
    }

    private val characterDetailsViewModel = CharacterDetailsViewModel()

    @Before
    fun setUp() {
        characterDetailsViewModel.responseHandler = responseHandlerMock
        characterDetailsViewModel.marvelClient = marvelClientMock
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(marvelClientMock, responseHandlerMock)
    }

    @Test
    fun `when fetching hero by id with success`() =

        runBlocking {
            // given
            val heroId = 35224

            // when
            characterDetailsViewModel.fetchHeroComics(heroId)

            // should
            verify(marvelClientMock).getComicsByHeroId(heroId)
            verify(responseHandlerMock).handleSuccess(mockComicsResponse)

            Unit
        }

    @Test
    fun `when fetching hero by id with error`() =

        runBlocking {

            // given
            val heroId = 35224
            val exception = Exception("")

            marvelClientMock.stub {
                onBlocking {
                    getComicsByHeroId(any())
                } doThrow exception
            }

            // when
            characterDetailsViewModel.fetchHeroComics(heroId)

            // should
            verify(marvelClientMock).getComicsByHeroId(heroId)
            verify(responseHandlerMock).handleException<MarvelComicsResponse>(exception)

            Unit
        }
}