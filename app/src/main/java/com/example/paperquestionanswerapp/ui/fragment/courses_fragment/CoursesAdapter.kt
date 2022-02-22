package com.example.paperquestionanswerapp.ui.fragment.courses_fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.paperquestionanswerapp.databinding.ItemCourseBinding
import com.example.paperquestionanswerapp.model.CourseModel
import javax.inject.Inject

/**
 * Created by Agam on 22-02-2022.
 */

const val TAG = "CoursesAdapter"

class CoursesAdapter @Inject constructor(
    private val glide: RequestManager
) :
    ListAdapter<CourseModel, CoursesAdapter.CourseViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding =
            ItemCourseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CourseModel) {
            Log.d(TAG, "bind: courseModel : $item")
            glide.load(item.image).into(binding.courseImg)
            binding.courseNameTxt.text = item.name

            binding.parentCardView.setOnClickListener {
                onCourseClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onCourseClickListener: ((CourseModel) -> Unit)? = null
    fun setOnCourseClickListener(courseModel: (CourseModel) -> Unit) {
        onCourseClickListener = courseModel
    }

    class DiffCallback : DiffUtil.ItemCallback<CourseModel>() {
        override fun areItemsTheSame(oldItem: CourseModel, newItem: CourseModel) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: CourseModel, newItem: CourseModel) =
            oldItem == newItem
    }
}