package com.example.paperquestionanswerapp.ui.fragment.question_answer_fragment

import android.util.Log
import androidx.lifecycle.*
import com.example.paperquestionanswerapp.model.PaperQueAnsModel
import com.example.paperquestionanswerapp.model.UserSelectedPaper
import com.example.paperquestionanswerapp.repository.schema.CourseAndPaperDetailsRepository
import com.example.paperquestionanswerapp.util.Event
import com.example.paperquestionanswerapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Agam on 01-03-2022.
 */
const val TAGs = "QuestionAnswerViewModel"
@HiltViewModel
class QuestionAnswerViewModel @Inject constructor(
    private val courseAndPaperDetailsRepository: CourseAndPaperDetailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _paperQueAns = MutableLiveData<Event<Resource<PaperQueAnsModel?>>>()
    val paperQueAns: LiveData<Event<Resource<PaperQueAnsModel?>>> = _paperQueAns

    init {
        val userSelectedPaper = state.get<UserSelectedPaper>("userSelectedPaper")
        getPaperQueAns(userSelectedPaper)
    }

     fun getPaperQueAns(userSelectedPaper: UserSelectedPaper?) {
         Log.d(TAGs, "getPaperQueAns: $userSelectedPaper")
        viewModelScope.launch(dispatcher) {
            userSelectedPaper?.let { userSelectedPaper ->
                val result = courseAndPaperDetailsRepository.getPaperQueAns(
                    course = userSelectedPaper.courseName,
//                    semester = userSelectedPaper.semesterName,
//                    subject = userSelectedPaper.subjectName,
                    paperId = userSelectedPaper.paperId
                )
                Log.d(TAGs, "getPaperQueAns: result is $result")
                _paperQueAns.postValue(Event(result))
            }
        }
    }
}
