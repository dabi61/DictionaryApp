package com.example.dictionaryapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "han_tu")
data class HanTu (
    @PrimaryKey(autoGenerate = true)
    val _id: Int?,
    val han: String?,
    val viet: String?,
    val pinyin: String?,
    val mean: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            if (_id != null) {
                parcel.writeInt(_id)
            }
        parcel.writeString(han)
        parcel.writeString(viet)
        parcel.writeString(pinyin)
        parcel.writeString(mean)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HanTu> {
        override fun createFromParcel(parcel: Parcel): HanTu {
            return HanTu(parcel)
        }

        override fun newArray(size: Int): Array<HanTu?> {
            return arrayOfNulls(size)
        }
    }
}