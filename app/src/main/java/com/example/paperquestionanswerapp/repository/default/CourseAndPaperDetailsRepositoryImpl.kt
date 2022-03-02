package com.example.paperquestionanswerapp.repository.default

import android.util.Log
import com.example.paperquestionanswerapp.model.CourseModel
import com.example.paperquestionanswerapp.model.PaperQueAnsModel
import com.example.paperquestionanswerapp.model.SubjectModel
import com.example.paperquestionanswerapp.repository.schema.CourseAndPaperDetailsRepository
import com.example.paperquestionanswerapp.util.COURSES_COLLECTION
import com.example.paperquestionanswerapp.util.Resource
import com.example.paperquestionanswerapp.util.safeCall
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Agam on 20-02-2022.
 */

private const val TAG = "CourseAndPaperRepo"

class CourseAndPaperDetailsRepositoryImpl @Inject constructor(private val fireStore: FirebaseFirestore) :
    CourseAndPaperDetailsRepository {

    // Collections
    private val coursesCollection = fireStore.collection(COURSES_COLLECTION)

    // Return all available Courses
    override suspend fun getCourses(): Resource<List<CourseModel>> = withContext(IO) {
        safeCall {
            val courses = coursesCollection.get().await().toObjects(CourseModel::class.java)
            Resource.Success(courses)
        }
    }

    // Return all subject list for particular semester
    override suspend fun getSubjects(
        course: String,
        semester: String
    ): Resource<List<SubjectModel>> = withContext(IO) {
        safeCall {
            val subjectCollection = coursesCollection.document(course).collection(semester)
            val subjects = subjectCollection.get().await().toObjects(SubjectModel::class.java)

            Resource.Success(subjects)
        }
    }

    override suspend fun getPaperQueAns(
        course: String,
//        semester: String,
//        subject: String,
        paperId: String
    ): Resource<PaperQueAnsModel?> = withContext(IO) {
        safeCall {
//            val queAnsCollection =
//                fireStore.collection(course).document(semester).collection(subject)
//                    .document(paperId)
            val queAnsCollection =
                fireStore.collection(course).document(paperId)
            Log.d(TAG, "getPaperQueAns: collection : ${queAnsCollection.path}")
            val paperQueAnsModel: PaperQueAnsModel? =
                queAnsCollection.get().await().toObject(PaperQueAnsModel::class.java)
            Log.d(TAG, "getPaperQueAns: $paperQueAnsModel")
            Resource.Success(paperQueAnsModel)
        }
    }
}