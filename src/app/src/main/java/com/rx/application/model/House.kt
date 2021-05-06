package com.rx.application.model

import android.os.Parcel
import android.os.Parcelable

data class House(
    val no: Int?,
    val category: String?,
    val name: String?,
    val pic: String?,
    val info: String?,
    val memo: String?,
    val geo: String?,
    val url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
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
        parcel.writeValue(no)
        parcel.writeString(category)
        parcel.writeString(name)
        parcel.writeString(pic)
        parcel.writeString(info)
        parcel.writeString(memo)
        parcel.writeString(geo)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<House> {
        override fun createFromParcel(parcel: Parcel): House {
            return House(parcel)
        }

        override fun newArray(size: Int): Array<House?> {
            return arrayOfNulls(size)
        }
    }
}
