package com.example.dictionaryapp.database.dictionary

import androidx.room.Dao
import androidx.room.Query
import com.example.dictionaryapp.model.VietTrung

@Dao
interface TranslateDao {
    @Query("SELECT content FROM viet_trung WHERE word LIKE '%' || :word || '%' OR search LIKE '%' || :search || '%'")
    fun translateVT(word: String, search: String): VietTrung


}