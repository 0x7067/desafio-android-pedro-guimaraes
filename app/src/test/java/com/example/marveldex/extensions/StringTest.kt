package com.example.marveldex.extensions

import org.junit.Assert.*
import org.junit.Test

class StringTest {

    @Test
    fun `correctly encodes a string with md5`() {
        // given
        val testString = "hello world"

        // when
        val testStringMD5 = testString.toMD5()

        // should
        assertEquals("5eb63bbbe01eeed093cb22bb8f5acdc3", testStringMD5)
    }
}