package com.example.dictionaryapp.vocabUi

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.ProfileFragment
import com.example.dictionaryapp.activityUi.MainActivity
import com.example.dictionaryapp.myInterface.HanTuClick
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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class VocabFragment : Fragment(), HanTuClick {
    lateinit var listHanTu : MutableList<HanTu>
    lateinit var adapterHanTu: HanTuAdapter
    private  lateinit var dictionaryDao: DictionaryDao
    private var coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var binding: FragmentVocabBinding

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        requireActivity().supportFragmentManager.beginTransaction()
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
    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}