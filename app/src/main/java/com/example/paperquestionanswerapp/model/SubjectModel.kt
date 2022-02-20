package com.example.paperquestionanswerapp.model

/**
 * Created by Agam on 13-02-2022.
 */

data class SubjectModel(
    val name: String = "",
    val description: String = "",
    val paperDetails : List<PaperDetailsModel>
)
