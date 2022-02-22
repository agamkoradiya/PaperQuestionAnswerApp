package com.example.paperquestionanswerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Agam on 13-02-2022.
 */

@Parcelize
data class CourseModel(
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val semesters: List<String> = emptyList()
) : Parcelable
