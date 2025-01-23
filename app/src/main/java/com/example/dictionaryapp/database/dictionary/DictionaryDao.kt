package com.example.dictionaryapp.database.dictionary

import androidx.room.Dao
import androidx.room.Query
import com.example.dictionaryapp.model.HanTu

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM han_tu LIMIT 100")
    fun getAllDictionary() : MutableList<HanTu>

    @Query("SELECT COUNT(*) FROM han_tu")
    fun getRowCount(): Int

    @Query("SELECT * FROM han_tu WHERE han LIKE '%' || :han || '%' OR viet LIKE '%' || :viet || '%'")
    fun getListNote(han: String, viet: String): MutableList<HanTu>
}