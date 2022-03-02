package com.example.paperquestionanswerapp.ui.fragment.courses_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.paperquestionanswerapp.databinding.FragmentCoursesBinding
import com.example.paperquestionanswerapp.model.CourseModel
import com.example.paperquestionanswerapp.util.EventObserver
import com.example.paperquestionanswerapp.util.hide
import com.example.paperquestionanswerapp.util.show
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "CourseFragment"

@AndroidEntryPoint
class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!

    private val coursesSemesterViewModel by viewModels<CoursesViewModel>()

    // Global Selected Items
    lateinit var selectedCourseName: String
    lateinit var selectedSemester: String
    var selectedItemIndex = 0

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

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "PaperQuestionAnswer"

        setUpRecyclerView()
        observeData()
    }

    private fun setUpRecyclerView() {
        binding.coursesRecycleView.adapter = coursesAdapter
        coursesAdapter.setOnCourseClickListener {
            showSemesterSelectionDialog(it)
        }
    }

    private fun showSemesterSelectionDialog(courseModel: CourseModel) {
        selectedCourseName = courseModel.name
        selectedSemester = courseModel.semesters.first()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select Your Semester")
            .setSingleChoiceItems(
                courseModel.semesters.toTypedArray(),
                selectedItemIndex
            ) { _, which ->
                Log.d(
                    TAG,
                    "showSemesterSelectionDialog: $which - ${courseModel.semesters[which]}"
                )
                selectedItemIndex = which
                selectedSemester = courseModel.semesters[which]
            }
            .setPositiveButton("Proceed") { _, _ ->
                val action = CoursesFragmentDirections.actionCoursesFragmentToSubjectsFragment(
                    selectedCourseName,
                    selectedSemester
                )
                findNavController().navigate(action)
            }
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun observeData() {
        coursesSemesterViewModel.courses.observe(viewLifecycleOwner, EventObserver(
            onError = {
                Log.d(TAG, "observeData: Error : $it")
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