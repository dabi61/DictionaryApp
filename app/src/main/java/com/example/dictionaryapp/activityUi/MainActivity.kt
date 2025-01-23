package com.example.dictionaryapp.activityUi

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.dictionaryapp.R
import com.example.dictionaryapp.adapter.ViewPagerAdapter
import com.example.dictionaryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val adapter = ViewPagerAdapter(this)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            else {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }
        }
        window.navigationBarColor = ContextCompat.getColor(this, R.color.nav_bar)
        window.statusBarColor = ContextCompat.getColor(this, R.color.body_space)

        binding.vpMain.adapter = adapter


        binding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bnMenu.selectedItemId = R.id.i_vocab
                    1 -> binding.bnMenu.selectedItemId = R.id.i_search
                    2 -> binding.bnMenu.selectedItemId = R.id.i_profile
                }
            }
        })

        binding.bnMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.i_vocab -> {
                    binding.vpMain.currentItem = 0
                    true
                }
                R.id.i_search -> {
                    binding.vpMain.currentItem = 1
                    true
                }
                R.id.i_profile -> {
                    binding.vpMain.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }
}