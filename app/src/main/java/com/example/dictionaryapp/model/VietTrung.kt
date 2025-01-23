package com.example.dictionaryapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable