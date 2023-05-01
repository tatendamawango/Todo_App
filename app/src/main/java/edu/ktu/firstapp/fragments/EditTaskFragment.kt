package edu.ktu.firstapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import edu.ktu.firstapp.databinding.FragmentEditTaskBinding
import edu.ktu.firstapp.viewModels.EditTaskViewModel
import java.util.*


class EditTaskFragment : Fragment() {
    private lateinit var binding: FragmentEditTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        val viewModel: EditTaskViewModel by viewModels()

        binding.lifecycleOwner = viewLifecycleOwner

        binding.dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->
                    binding.dateEditText.setText("$dayOfMonth/${monthOfYear + 1}/$year")
                },
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.show()
        }

        binding.saveBtn.setOnClickListener {
            viewModel.editTask(
                binding.descriptionEditText.text.toString(),
                binding.dateEditText.text.toString()
            )
            findNavController().popBackStack()
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }



        return binding.root
    }


}