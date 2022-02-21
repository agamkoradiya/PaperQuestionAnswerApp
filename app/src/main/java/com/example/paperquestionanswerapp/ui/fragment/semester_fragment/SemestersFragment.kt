package com.example.paperquestionanswerapp.ui.fragment.semester_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paperquestionanswerapp.R
import com.example.paperquestionanswerapp.databinding.FragmentSemestersBinding

class SemestersFragment : Fragment() {

    private var _binding: FragmentSemestersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSemestersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}