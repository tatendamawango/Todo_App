package edu.ktu.firstapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import edu.ktu.firstapp.R
import edu.ktu.firstapp.databinding.FragmentTodoBinding

class TodoFragment : Fragment() {

    private lateinit var binding: FragmentTodoBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        mainEvent()
    }

    private fun mainEvent() {
        binding.signOutBtn.setOnClickListener {
            auth.signOut()
            navController.navigate(R.id.action_todoFragment_to_loginFragment)
        }

        binding.editProfileBtn.setOnClickListener {
            navController.navigate(R.id.action_todoFragment_to_editProfileFragment)
        }

        binding.newTaskBtn.setOnClickListener {
            navController.navigate(R.id.action_todoFragment_to_newTaskFragment)
        }

        binding.tasksBtn.setOnClickListener {
            navController.navigate(R.id.action_todoFragment_to_tasksFragment)
        }

        binding.inProgressBtn.setOnClickListener {
            navController.navigate(R.id.action_todoFragment_to_inProgressFragment)
        }

        binding.doneBtn.setOnClickListener {
            navController.navigate(R.id.action_todoFragment_to_finishedFragment)
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

}