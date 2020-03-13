package com.moisespedro.marveldex.api

import com.moisespedro.marveldex.BuildConfig
import com.moisespedro.marveldex.extensions.toMD5
import okhttp3.Interceptor
import okhttp3.Response

class MarvelApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentTimestamp = System.currentTimeMillis()
        val hash =
            (currentTimestamp.toString() + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLIC_KEY).toMD5()

        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("ts", currentTimestamp.toString())
            .addQueryParameter("apikey", BuildConfig.PUBLIC_KEY)
            .addQueryParameter("hash", hash)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}