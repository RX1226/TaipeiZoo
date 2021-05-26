package com.rx.application.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plant(
    val name: String?,
    val alias: String?,
    val geo: String?,
    val location: String?,
    val nameEnglish: String?,
    val nameLatin: String?,
    val family: String?,
    val genus: String?,
    val brief: String?,
    val feature: String?,
    val usefulness: String?,
    val pic1Alt: String?,
    val pic1Url: String?,
    val pic2Alt: String?,
    val pic2Url: String?,
    val lastUpdate: String?
): Parcelable