package com.example.marveldex.data

import org.junit.Assert.assertEquals
import org.junit.Test

class MarvelThumbnailTest {

    @Test
    fun `correctly creates thumbnail url`() {
        //given
        val path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784"
        val extension = "jpg"

        // when
        val thumbnail = MarvelThumbnail(path, extension)

        // should
        assertEquals("https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg", thumbnail.getUrl())
    }
}

