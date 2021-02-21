package com.moisespedro.marveldex.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HeroDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String
) : Parcelable