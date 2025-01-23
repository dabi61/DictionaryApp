package com.example.dictionaryapp.vocabUi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dictionaryapp.databinding.FragmentDetailVocabBinding
import com.example.dictionaryapp.model.HanTu

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val HAN_TU = "hantu"
private const val ARG_PARAM2 = "param2"

class DetailVocabFragment() : Fragment() {
    private lateinit var binding : FragmentDetailVocabBinding
    private var hantu: HanTu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            hantu = it.getParcelable(HAN_TU)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailVocabBinding.inflate(inflater, container, false)
        with(binding) {
            tvHan.text = hantu?.han
            tvViet.text = hantu?.viet
            tvPinyin.text = "(${hantu?.pinyin})"
            tvMean.text = hantu?.mean
            tvApp.text = hantu?.han
        }

        binding.ivBack.setOnClickListener() {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    companion object {
        fun newInstance(hantu : HanTu): DetailVocabFragment {
            val fragment = DetailVocabFragment()
            val args = Bundle()
           args.putParcelable(HAN_TU, hantu)
            fragment.arguments = args
            return fragment
        }
    }

}