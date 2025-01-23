package com.example.dictionaryapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dictionaryapp.database.dictionary.DictionaryDatabase
import com.example.dictionaryapp.database.dictionary.TranslateDao
import com.example.dictionaryapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentSearchBinding
    private  lateinit var translateDao: TranslateDao
    private var coroutineScope = CoroutineScope(Dispatchers.IO)

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
    ): View? {
        val db = DictionaryDatabase.getInstance(requireActivity())
        translateDao = db.translateDao()
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.etBox1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Không cần làm gì ở đây
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                coroutineScope.launch {
                    val word = translateDao.translateVT(s.toString(), s.toString())
                    if(word != null)
                    {
                        withContext(Dispatchers.Main) {
                            binding.etBox2.text = normalizeText(word.content.toString())
                        }
                    }
                    else{
                        withContext(Dispatchers.Main) {
                            binding.etBox2.text = ""
                        }
                    }

                }
            }
            override fun afterTextChanged(s: Editable?) {
                // Không cần làm gì ở đây
            }
        })

        binding.ivSwap.visibility = View.GONE
//        binding.ivSwap.setOnClickListener {
//            val temp = binding.tvLanguage1.text
//            binding.tvLanguage1.text = binding.tvLanguage2.text
//            binding.tvLanguage2.text = temp
//            val temp2 = binding.etBox1.text
//            binding.etBox1.setText(binding.etBox2.text)
//            binding.etBox2.text = temp2
//        }

        return binding.root
    }
    private fun normalizeText(input: String): String {
        return input
            .replace("\\\\", "'\\'")
            .replace("\\n", " ")
            .replace("\\t", ".")
            .replace("<br>&nbsp;", ".")
            .trim()
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}