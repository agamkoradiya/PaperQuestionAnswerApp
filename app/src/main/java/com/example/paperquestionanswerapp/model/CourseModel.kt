package com.example.paperquestionanswerapp.model

/**
 * Created by Agam on 13-02-2022.
 */

data class CourseModel(
    val name: String = "",
    val image: String = "",
    val description: String = "",
    val semester: List<String> = emptyList()
)
