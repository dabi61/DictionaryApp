package com.example.dictionaryapp.VocabUi

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.ActivityUi.MainActivity
import com.example.dictionaryapp.MyInterface.HanTuClick
import com.example.dictionaryapp.R
import com.example.dictionaryapp.adapter.HanTuAdapter
import com.example.dictionaryapp.database.dictionary.DictionaryDao
import com.example.dictionaryapp.database.dictionary.DictionaryDatabase
import com.example.dictionaryapp.databinding.FragmentVocabBinding
import com.example.dictionaryapp.model.HanTu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.IOException


class VocabFragment : Fragment(), HanTuClick {
    lateinit var listHanTu : MutableList<HanTu>
    lateinit var adapterHanTu: HanTuAdapter
    private  lateinit var dictionaryDao: DictionaryDao
    private var coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var binding: FragmentVocabBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVocabBinding.inflate(inflater, container, false)
        val db = DictionaryDatabase.getInstance(requireActivity())
        dictionaryDao = db.dictionaryDao()
        loadData(dictionaryDao)

        listHanTu = mutableListOf()
        adapterHanTu = HanTuAdapter(requireActivity() , listHanTu, this)
        binding.rvVocab.apply {
            adapter = adapterHanTu
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
        }
        binding.ivSearch.setOnClickListener{
            with(binding){
                etSearch.visibility = View.VISIBLE
                ivBack.visibility = View.VISIBLE
                ivSearch.visibility = View.GONE
                tvApp.visibility = View.GONE
            }

        }
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Không cần làm gì ở đây
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                coroutineScope.launch {
                    val listDictionary = dictionaryDao.getListNote(s.toString(), s.toString())
                    withContext(Dispatchers.Main) {
                        adapterHanTu.updateData(listDictionary)
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {
                // Không cần làm gì ở đây
            }
        })


        binding.ivBack.setOnClickListener{
            with(binding){
                etSearch.visibility = View.GONE
                ivBack.visibility = View.GONE
                ivSearch.visibility = View.VISIBLE
                tvApp.visibility = View.VISIBLE
                etSearch.text?.clear()
            }
        }

        return binding.root
    }

    override fun onHanTuClick(hantu: HanTu) {
        val detailVocabFragment = DetailVocabFragment.newInstance(hantu)
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .add(R.id.fl_main, detailVocabFragment)
            .addToBackStack(null)
            .commit()
    }
    private fun loadData(dictionaryDao: DictionaryDao) {
        coroutineScope.launch {
            val listDictionary = dictionaryDao.getAllDictionary()
            listHanTu.addAll(listDictionary)
            withContext(Dispatchers.Main) {
                adapterHanTu.notifyDataSetChanged()
            }
        }
    }

}