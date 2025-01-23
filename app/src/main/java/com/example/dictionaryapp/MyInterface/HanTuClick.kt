package com.example.dictionaryapp.MyInterface

import androidx.appcompat.view.menu.MenuView.ItemView
import com.example.dictionaryapp.model.HanTu

interface HanTuClick {
        fun onHanTuClick(hantu: HanTu)
}