package com.rx.application.model

import android.os.Parcel
import android.os.Parcelable

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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(alias)
        parcel.writeString(geo)
        parcel.writeString(location)
        parcel.writeString(nameEnglish)
        parcel.writeString(nameLatin)
        parcel.writeString(family)
        parcel.writeString(genus)
        parcel.writeString(brief)
        parcel.writeString(feature)
        parcel.writeString(usefulness)
        parcel.writeString(pic1Alt)
        parcel.writeString(pic1Url)
        parcel.writeString(pic2Alt)
        parcel.writeString(pic2Url)
        parcel.writeString(lastUpdate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Plant> {
        override fun createFromParcel(parcel: Parcel): Plant {
            return Plant(parcel)
        }

        override fun newArray(size: Int): Array<Plant?> {
            return arrayOfNulls(size)
        }
    }
}