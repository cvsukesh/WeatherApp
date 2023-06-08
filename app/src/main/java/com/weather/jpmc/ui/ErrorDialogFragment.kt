package com.weather.jpmc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.weather.jpmc.databinding.ErrorFragmentBinding

class ErrorDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(errorTitle: String, errorMessage: String): ErrorDialogFragment {
            return ErrorDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ERROR_TITLE, errorTitle)
                    putString(ERROR_MESSAGE, errorMessage)
                }
            }
        }

        const val ERROR_TITLE = "error_title"
        const val ERROR_MESSAGE = "error_message"
    }

    private lateinit var binding: ErrorFragmentBinding

    private val errorTitle: String by lazy {
        arguments?.getString(ERROR_TITLE) ?: "Error"
    }

    private val errorMessage: String by lazy {
        arguments?.getString(ERROR_MESSAGE) ?: "Something Went Wrong"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ErrorFragmentBinding.inflate(inflater, container, false)

        binding.errorTitle.text = errorTitle
        binding.errorMessage.text = errorMessage
        binding.okay.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}