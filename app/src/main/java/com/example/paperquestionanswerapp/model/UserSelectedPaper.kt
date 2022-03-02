package com.example.paperquestionanswerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Agam on 28-02-2022.
 */

@Parcelize
data class UserSelectedPaper(
    val courseName: String,
    val semesterName: String,
    val subjectName: String,
    val paperId: String,
    val paperName: String
) : Parcelable
