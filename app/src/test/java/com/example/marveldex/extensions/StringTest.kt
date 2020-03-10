package com.example.marveldex.extensions

import org.junit.Assert.*
import org.junit.Test

class StringTest {

    @Test
    fun checkStringMD5isCorrect() {
        val testString = "hello world"
        assertEquals("5eb63bbbe01eeed093cb22bb8f5acdc3", testString.toMD5())
    }
}