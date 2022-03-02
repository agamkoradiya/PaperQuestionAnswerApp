package com.example.paperquestionanswerapp.ui.fragment.question_answer_fragment

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paperquestionanswerapp.databinding.ItemQueAnsBinding
import com.example.paperquestionanswerapp.model.QueAnsModel
import com.example.paperquestionanswerapp.util.toggleArrow
import javax.inject.Inject

/**
 * Created by Agam on 02-03-2022.
 */

class QueAnsAdapter @Inject constructor() :
    ListAdapter<QueAnsModel, QueAnsAdapter.QueAnsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueAnsViewHolder {
        val binding =
            ItemQueAnsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return QueAnsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QueAnsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class QueAnsViewHolder(private val binding: ItemQueAnsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: QueAnsModel) {
            binding.queTxt.text = currentItem.que
            binding.ansTxt.text = currentItem.ans

            binding.showAnsBtn.setOnClickListener {
                it.toggleArrow(binding.layoutExpand.visibility == GONE)
                binding.layoutExpand.visibility = if (binding.layoutExpand.visibility == VISIBLE) GONE else VISIBLE
            }

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<QueAnsModel>() {
        override fun areItemsTheSame(oldItem: QueAnsModel, newItem: QueAnsModel) =
            oldItem.que == newItem.que

        override fun areContentsTheSame(oldItem: QueAnsModel, newItem: QueAnsModel) =
            oldItem == newItem
    }
}