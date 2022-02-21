package com.example.paperquestionanswerapp.ui.shared_view_models

import androidx.lifecycle.*
import com.example.paperquestionanswerapp.model.CourseModel
import com.example.paperquestionanswerapp.repository.schema.CourseAndPaperDetailsRepository
import com.example.paperquestionanswerapp.util.Event
import com.example.paperquestionanswerapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Agam on 21-02-2022.
 */

@HiltViewModel
class CoursesSemesterViewModel @Inject constructor(
    private val courseAndPaperDetailsRepository: CourseAndPaperDetailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _courses = MutableLiveData<Event<Resource<List<CourseModel>>>>()
    val courses: LiveData<Event<Resource<List<CourseModel>>>> = _courses

    init {
        getCourses()
    }

    private fun getCourses() {
        _courses.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = courseAndPaperDetailsRepository.getCourses()
            _courses.postValue(Event(result))
        }
    }
}