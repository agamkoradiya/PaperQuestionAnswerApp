package com.example.paperquestionanswerapp.ui.fragment.subjects_fragment

import androidx.lifecycle.*
import com.example.paperquestionanswerapp.model.SubjectModel
import com.example.paperquestionanswerapp.repository.schema.CourseAndPaperDetailsRepository
import com.example.paperquestionanswerapp.util.Event
import com.example.paperquestionanswerapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Agam on 27-02-2022.
 */


@HiltViewModel
class SubjectsViewModel @Inject constructor(
    private val courseAndPaperDetailsRepository: CourseAndPaperDetailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _subjects = MutableLiveData<Event<Resource<List<SubjectModel>>>>()
    val subjects: LiveData<Event<Resource<List<SubjectModel>>>> = _subjects

    init {
        val courseName = state.get<String>("courseName")
        val semesterName = state.get<String>("semesterName")
        if (!courseName.isNullOrEmpty() && !semesterName.isNullOrEmpty())
            getSubjects(courseName, semesterName)
    }

    fun getSubjects(courseName: String, semesterName: String) {
        _subjects.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = courseAndPaperDetailsRepository.getSubjects(courseName, semesterName)
            _subjects.postValue(Event(result))
        }
    }

}