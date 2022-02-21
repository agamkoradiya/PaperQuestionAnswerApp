package com.example.paperquestionanswerapp.repository.schema

import com.example.paperquestionanswerapp.model.CourseModel
import com.example.paperquestionanswerapp.model.SubjectModel
import com.example.paperquestionanswerapp.util.Resource

/**
 * Created by Agam on 20-02-2022.
 */

interface CourseAndPaperDetailsRepository {

    suspend fun getCourses(): Resource<List<CourseModel>>

    suspend fun getSubjects(course: String, semester: String): Resource<List<SubjectModel>>
}