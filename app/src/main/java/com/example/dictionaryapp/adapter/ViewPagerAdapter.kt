package com.example.dictionaryapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dictionaryapp.ProfileFragment
import com.example.dictionaryapp.SearchFragment
import com.example.dictionaryapp.vocabUi.VocabFragment

class ViewPagerAdapter(FragmentActivity: FragmentActivity) : FragmentStateAdapter(FragmentActivity) {
    override fun getItemCount(): Int = 3


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> VocabFragment()
            1 -> SearchFragment()
            2 -> ProfileFragment()
            else -> VocabFragment()
        }
    }

}
