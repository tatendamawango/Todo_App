package edu.ktu.firstapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import edu.ktu.firstapp.R
import edu.ktu.firstapp.databinding.FragmentSignupBinding
import edu.ktu.firstapp.viewModels.SignUpViewModel

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val viewModel: SignUpViewModel by viewModels()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.currentUser.observe(viewLifecycleOwner){
            it?.let {
                findNavController().navigate(R.id.action_signupFragment_to_todoFragment)
            }
        }

        binding.registerBtn.setOnClickListener {
            viewModel.signUp(
                binding.registerEmailAddress.text.toString(),
                binding.registerPassword.text.toString(),
                binding.confirmRegisterPassword.text.toString()
            )
        }

        binding.RegisterLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        return binding.root
    }
}