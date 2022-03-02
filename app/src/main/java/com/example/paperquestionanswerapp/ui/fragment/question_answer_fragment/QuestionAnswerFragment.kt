package com.example.paperquestionanswerapp.ui.fragment.question_answer_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.paperquestionanswerapp.databinding.FragmentQuestionAnswerBinding
import com.example.paperquestionanswerapp.util.EventObserver
import com.example.paperquestionanswerapp.util.hide
import com.example.paperquestionanswerapp.util.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "QuestionAnswer"

@AndroidEntryPoint
class QuestionAnswerFragment : Fragment() {

    private var _binding: FragmentQuestionAnswerBinding? = null
    private val binding get() = _binding!!

    private val questionAnswerViewModel by viewModels<QuestionAnswerViewModel>()
    private val args by navArgs<QuestionAnswerFragmentArgs>()

    @Inject
    lateinit var queAnsAdapter: QueAnsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionAnswerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            args.userSelectedPaper.paperName

        setUpRecyclerView()
        observeData()
    }

    private fun observeData() {
        questionAnswerViewModel.paperQueAns.observe(viewLifecycleOwner, EventObserver(
            onError = {
                Log.d(TAG, "observeData: Error : $it")
                binding.progressBar.hide()
                Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE).setAction("Retry") {
                    questionAnswerViewModel.getPaperQueAns(args.userSelectedPaper)
                }.show()
            },
            onLoading = {
                binding.progressBar.show()
            }
        ) {
            binding.progressBar.hide()
            Log.d(TAG, "observeData: PaperQueAnsModel - $it")
            queAnsAdapter.submitList(it?.paperQueAns)
        })
    }

    private fun setUpRecyclerView() {
        binding.queAnsRecycleView.adapter = queAnsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}