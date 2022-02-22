package com.example.paperquestionanswerapp.ui.fragment.courses_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.paperquestionanswerapp.databinding.FragmentCoursesBinding
import com.example.paperquestionanswerapp.ui.shared_view_models.CoursesSemesterViewModel
import com.example.paperquestionanswerapp.util.EventObserver
import com.example.paperquestionanswerapp.util.hide
import com.example.paperquestionanswerapp.util.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private val coursesSemesterViewModel by viewModels<CoursesSemesterViewModel>()

    @Inject
    lateinit var coursesAdapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observeData()
    }

    private fun setUpRecyclerView() {
        binding.coursesRecycleView.adapter = coursesAdapter
    }

    private fun observeData() {
        coursesSemesterViewModel.courses.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.progressBar.hide()
                Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
                    coursesSemesterViewModel.getCourses()
                }.show()
            },
            onLoading = {
                binding.progressBar.show()
            }
        ) {
            binding.progressBar.hide()
            coursesAdapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}