package com.example.marveldex.data

import com.google.gson.annotations.SerializedName

open class MarvelResponse(
    @SerializedName("code") protected var code: Int = 0,
    @SerializedName("status") protected var status: String = "",
    @SerializedName("copyright") protected var copyright: String = "",
    @SerializedName("attributionText") protected var attributionText: String = "",
    @SerializedName("attributionHTML") protected var attributionHTML: String = "",
    @SerializedName("etag") protected var etag: String = ""
) : IMarvelResponse {
    override fun isSuccess(): Boolean {
        return code == 200
    }

    override fun getErrorMessage(): String {
        return status
    }
}