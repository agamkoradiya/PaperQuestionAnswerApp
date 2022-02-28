package com.example.paperquestionanswerapp.ui.fragment.subjects_fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.paperquestionanswerapp.databinding.ItemSubjectBinding
import com.example.paperquestionanswerapp.model.SubjectModel
import javax.inject.Inject

/**
 * Created by Agam on 22-02-2022.
 */

private const val TAG = "SubjectsAdapter"

class SubjectsAdapter @Inject constructor(
    private val glide: RequestManager
) : ListAdapter<SubjectModel, SubjectsAdapter.SubjectsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsViewHolder {
        val binding =
            ItemSubjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SubjectsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SubjectsViewHolder(private val binding: ItemSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SubjectModel) {
            Log.d(TAG, "bind: courseModel : $item")
            binding.courseNameTxt.text = item.name

            binding.parentCardView.setOnClickListener {
                onSubjectClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onSubjectClickListener: ((SubjectModel) -> Unit)? = null
    fun setOnSubjectClickListener(subjectModel: (SubjectModel) -> Unit) {
        onSubjectClickListener = subjectModel
    }

    class DiffCallback : DiffUtil.ItemCallback<SubjectModel>() {
        override fun areItemsTheSame(oldItem: SubjectModel, newItem: SubjectModel) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: SubjectModel, newItem: SubjectModel) =
            oldItem == newItem
    }
}