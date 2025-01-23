package com.example.dictionaryapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "han_tu")
data class HanTu (
    @PrimaryKey(autoGenerate = true)
    val _id: Int?,
    val han: String?,
    val viet: String?,
    val pinyin: String?,
    val mean: String?
) : Parcelable
