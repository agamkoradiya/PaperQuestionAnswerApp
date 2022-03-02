package com.example.paperquestionanswerapp.ui.fragment.subjects_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.paperquestionanswerapp.databinding.FragmentSubjectsBinding
import com.example.paperquestionanswerapp.model.PaperDetailsModel
import com.example.paperquestionanswerapp.model.SubjectModel
import com.example.paperquestionanswerapp.model.UserSelectedPaper
import com.example.paperquestionanswerapp.ui.activity.MainActivity
import com.example.paperquestionanswerapp.util.EventObserver
import com.example.paperquestionanswerapp.util.hide
import com.example.paperquestionanswerapp.util.show
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "SubjectsFragment"

@AndroidEntryPoint
class SubjectsFragment : Fragment() {

    private var _binding: FragmentSubjectsBinding? = null
    private val binding get() = _binding!!

    private val subjectsViewModel by viewModels<SubjectsViewModel>()
    private val args by navArgs<SubjectsFragmentArgs>()

    @Inject
    lateinit var subjectsAdapter: SubjectsAdapter

    private var courseName: String? = null
    private var semesterName: String? = null

    // Global Selected Items
    lateinit var selectedSubjectName: String
    lateinit var selectedPaper: PaperDetailsModel
    var selectedItemIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPreviousData()
        setUpRecyclerView()
        observeData()
    }

    private fun getPreviousData() {
        courseName = args.courseName
        semesterName = args.semesterName
        (requireActivity() as AppCompatActivity).supportActionBar?.title = courseName
    }

    private fun setUpRecyclerView() {
        binding.subjectsRecycleView.adapter = subjectsAdapter
        subjectsAdapter.setOnSubjectClickListener {
            showPaperSelectionDialog(it)
        }
    }

    private fun showPaperSelectionDialog(subjectModel: SubjectModel) {
        selectedPaper = subjectModel.paperDetails[selectedItemIndex]

        val papers: List<String> = subjectModel.paperDetails.map { it.title }
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select Paper")
            .setSingleChoiceItems(
                papers.toTypedArray(),
                selectedItemIndex
            ) { _, which ->
                Log.d(
                    TAG,
                    "showSemesterSelectionDialog: $which - ${subjectModel.paperDetails[which]}"
                )
                selectedItemIndex = which
                selectedPaper = subjectModel.paperDetails[which]
            }
            .setPositiveButton("Submit") { _, _ ->
                if (!selectedPaper.underProcess!!) {
                    val userSelectedPaper = UserSelectedPaper(
                        courseName!!,
                        semesterName!!,
                        subjectModel.name,
                        selectedPaper.id,
                        selectedPaper.title
                    )
                    val action =
                        SubjectsFragmentDirections.actionSubjectsFragmentToQuestionAnswerFragment(
                            userSelectedPaper
                        )
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun observeData() {
        subjectsViewModel.subjects.observe(viewLifecycleOwner, EventObserver(
            onError = {
                Log.d(TAG, "observeData: Error : $it")
                binding.progressBar.hide()
                Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
                    subjectsViewModel.getSubjects(courseName!!, semesterName!!)
                }.show()
            },
            onLoading = {
                binding.progressBar.show()
            }
        ) {
            Log.d(TAG, "observeData: $it")
            binding.progressBar.hide()
            subjectsAdapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}