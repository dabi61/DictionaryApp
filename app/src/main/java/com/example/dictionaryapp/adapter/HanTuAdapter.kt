package com.example.dictionaryapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.myInterface.HanTuClick
import com.example.dictionaryapp.databinding.LayoutHantuItemBinding
import com.example.dictionaryapp.model.HanTu

class HanTuAdapter(
    private val context: Context,
    private val hanTuList: MutableList<HanTu>,
    private val listener: HanTuClick
) : RecyclerView.Adapter<HanTuAdapter.HanTuViewHolder>() {

    inner class HanTuViewHolder(val binding: LayoutHantuItemBinding) :RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(hanTu: HanTu) {
            with(binding) {
                tvHan.text = hanTu.han
                tvViet.text = hanTu.viet
                tvPinyin.text = "(${hanTu.pinyin})"
                tvMean.text = hanTu.mean?.let { normalizeText(it) }
            }
            itemView.setOnClickListener{
                listener.onHanTuClick(hanTu)
            }
        }

        private fun normalizeText(input: String): String {
            return input
                .replace("\\\\", "'\\'")
                .replace("\\n", " ")
                .replace("\\t", ".")
                .trim()
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HanTuAdapter.HanTuViewHolder {
        val binding = LayoutHantuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HanTuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HanTuAdapter.HanTuViewHolder, position: Int) {
        val hanTu = hanTuList[position]
        holder.bind(hanTu)
    }

    override fun getItemCount(): Int {
        return hanTuList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: MutableList<HanTu>) {
        hanTuList.clear()
        hanTuList.addAll(newList)
        notifyDataSetChanged()
    }


}