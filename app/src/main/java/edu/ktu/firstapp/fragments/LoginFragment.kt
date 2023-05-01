package edu.ktu.firstapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import edu.ktu.firstapp.R
import edu.ktu.firstapp.databinding.FragmentLoginBinding
import edu.ktu.firstapp.viewModels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val viewModel: LoginViewModel by viewModels()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.currentUser.observe(viewLifecycleOwner) {
            it?.let{
                findNavController().navigate(R.id.action_loginFragment_to_todoFragment)
            }
        }

        binding.loginBtn.setOnClickListener {
            viewModel.signIn(
                binding.loginEmailAddress.text.toString().trim(),
                binding.loginPassword.text.toString().trim()
            )
        }
        binding.LoginRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        return binding.root
    }
}