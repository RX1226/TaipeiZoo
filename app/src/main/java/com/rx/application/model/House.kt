package com.rx.application.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class House(
    val no: Int?,
    val category: String?,
    val name: String?,
    val pic: String?,
    val info: String?,
    val memo: String?,
    val geo: String?,
    val url: String?
) : Parcelable
