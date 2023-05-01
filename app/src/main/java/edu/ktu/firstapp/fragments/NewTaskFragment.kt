package edu.ktu.firstapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.ktu.firstapp.models.Task
import edu.ktu.firstapp.databinding.FragmentNewTaskBinding
import java.util.*

class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        var selectedDate: String?
        binding.dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    selectedDate = String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year)
                    binding.dateEditText.setText(selectedDate)
                },
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.show()
        }
        binding.backBtn.setOnClickListener {
            navController.popBackStack()
        }
        binding.addBtn.setOnClickListener {
            val description = binding.descriptionEditText.text.toString()
            val date = binding.dateEditText.text.toString()
            if (description.isNotEmpty() && date.isNotEmpty()) {
                onSaveTask(description, date, binding.descriptionEditText, binding.dateEditText)
            }else if (description.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter description", Toast.LENGTH_SHORT).show()
            } else if (date.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter date", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter description and date", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseref = FirebaseDatabase.getInstance().reference
            .child("Tasks").child(auth.currentUser?.uid.toString())
    }

    private fun onSaveTask(description: String, date: String, descriptionEditText: EditText, dateEditText: EditText) {
        databaseref.push().setValue(Task(this.toString(),description, date))
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Task added", Toast.LENGTH_SHORT).show()
                    descriptionEditText.text.clear()
                    dateEditText.text.clear()
                } else {
                    Toast.makeText(requireContext(), "Task not added", Toast.LENGTH_SHORT).show()
                }
            }
        }
}