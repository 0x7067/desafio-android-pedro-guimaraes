package com.moisespedro.marveldex.extensions

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String.toMD5(): String {
    return try {
        val md = MessageDigest.getInstance("MD5")
        val messageDigest = md.digest(this.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashtext = no.toString(16)
        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }
        hashtext
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
    }
}