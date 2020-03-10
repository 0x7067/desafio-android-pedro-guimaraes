package com.example.marveldex.data

interface IMarvelResponse {
    fun isSuccess(): Boolean

    fun getErrorMessage(): String
}