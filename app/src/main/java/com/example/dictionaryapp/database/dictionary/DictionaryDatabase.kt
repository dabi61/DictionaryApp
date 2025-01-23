package com.example.dictionaryapp.database.dictionary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionaryapp.Constance.DATABASE_NAME
import com.example.dictionaryapp.model.HanTu
import com.example.dictionaryapp.model.VietTrung

@Database(entities = [HanTu::class, VietTrung::class], version = 1)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
    abstract fun translateDao(): TranslateDao

    companion object {
        @Volatile
        private var instance: DictionaryDatabase? = null
        fun getInstance(context: Context): DictionaryDatabase {
            return instance ?: synchronized(this) {
                val instanceCreate =
                    Room.databaseBuilder(
                        context,
                        DictionaryDatabase::class.java,
                        DATABASE_NAME
                    )
                        .createFromAsset(DATABASE_NAME)
                        .build()
                instance = instanceCreate
                instance!!
            }
        }
    }

}