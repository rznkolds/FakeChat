package com.rk.fakechat.ui.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rk.fakechat.R
import com.rk.fakechat.common.setPicture
import com.rk.fakechat.common.showToast
import com.rk.fakechat.common.viewBinding
import com.rk.fakechat.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by viewModels()
    private var picture: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pictureProfile.setOnClickListener {
            intent()
        }

        binding.pictureAdd.setOnClickListener {
            intent()
        }

        binding.signUp.setOnClickListener {
            register()
        }

        initObservers()
    }

    private fun initObservers() {

        viewModel.result.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpToHome())
            } else {
                requireContext().showToast("E-mail zaten mevcut")
            }
        }

        viewModel.fail.observe(viewLifecycleOwner) {
            requireContext().showToast(it)
        }
    }

    private fun register() = with(binding) {

        val name = signUpId.text.toString()
        val email = signUpEmail.text.toString()
        val password = signUpPassword.text.toString()

        viewModel.register(name, email, password, picture)
    }

    private fun intent() = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "image/*"
        register.launch(this)
    }

    private val register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                picture = it.data?.data
                binding.pictureProfile.setPicture(it.data?.data.toString())
                binding.pictureAdd.visibility = View.INVISIBLE
            }
        }
}