package com.example.dictionaryapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "viet_trung",
    indices = [
        Index(value = ["search"], name = "vt_s"),
        Index(value = ["word"], name = "vt_w")
    ])

data class VietTrung (
    @PrimaryKey(autoGenerate = true)
    val _id: Int?,
    val word: String?,
    val content: String?,
    val search: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        if (_id != null) {
            parcel.writeInt(_id)
        }
        parcel.writeString(word)
        parcel.writeString(content)
        parcel.writeString(search)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VietTrung> {
        override fun createFromParcel(parcel: Parcel): VietTrung {
            return VietTrung(parcel)
        }

        override fun newArray(size: Int): Array<VietTrung?> {
            return arrayOfNulls(size)
        }
    }

}