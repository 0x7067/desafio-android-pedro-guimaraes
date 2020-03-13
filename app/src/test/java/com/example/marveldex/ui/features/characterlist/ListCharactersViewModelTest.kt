package com.example.marveldex.ui.features.characterlist

import com.example.marveldex.api.MarvelClient
import com.example.marveldex.data.heroes.MarvelHeroResponse
import com.example.marveldex.data.network.Resource
import com.example.marveldex.data.network.ResponseHandler
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ListCharactersViewModelTest() {

    private val mockHeroResponse = mock<MarvelHeroResponse>()

    private val mockHeroResponseResourceSuccess = mock<Resource<MarvelHeroResponse>>()

    private val mockHeroResponseResourceError = mock<Resource<MarvelHeroResponse>>()

    private val marvelClientMock = mock<MarvelClient> {
        onBlocking { getMarvelHeroes(any(), any()) } doReturn mockHeroResponse
    }

    private val responseHandlerMock = mock<ResponseHandler> {
        on { handleSuccess(mockHeroResponse) } doReturn mockHeroResponseResourceSuccess
        on { handleException<MarvelHeroResponse>(any()) } doReturn mockHeroResponseResourceError
    }

    private val listCharactersViewModel = ListCharactersViewModel()

    @Before
    fun setup() {
        listCharactersViewModel.responseHandler = responseHandlerMock
        listCharactersViewModel.marvelClient = marvelClientMock
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(marvelClientMock, responseHandlerMock)
    }

    @Test
    fun `when fetching marvel heroes with success`() =

        runBlocking {
            // given
            val offset = 35

            // when
            listCharactersViewModel.fetchMarvelHeroes(offset)

            // should
            verify(marvelClientMock).getMarvelHeroes(20, offset)
            verify(responseHandlerMock).handleSuccess(mockHeroResponse)

            Unit
        }

    @Test
    fun `when fetching marvel heroes with error`() =

        runBlocking {

            // given
            val offset = 35
            val exception = Exception("")

            marvelClientMock.stub {
                onBlocking {
                    getMarvelHeroes(any(), any()) } doThrow exception
                }

            // when
            listCharactersViewModel.fetchMarvelHeroes(offset)

            // should
            verify(marvelClientMock).getMarvelHeroes(20, offset)
            verify(responseHandlerMock).handleException<MarvelHeroResponse>(exception)

            Unit
        }

}