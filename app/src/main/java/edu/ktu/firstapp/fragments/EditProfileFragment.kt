package edu.ktu.firstapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import edu.ktu.firstapp.R
import edu.ktu.firstapp.databinding.FragmentEditProfileBinding
import edu.ktu.firstapp.viewModels.EditProfileViewModel

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val viewModel: EditProfileViewModel by viewModels()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.ebackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.saveBtn.setOnClickListener {
            viewModel.editProfile(
                binding.currentPassword.text.toString(),
                binding.newPassword.text.toString(),
                binding.confirmPassword.text.toString()
            )
            findNavController().popBackStack()
        }

        return binding.root
    }

}