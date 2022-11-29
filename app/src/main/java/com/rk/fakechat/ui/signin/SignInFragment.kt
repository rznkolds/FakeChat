package com.rk.fakechat.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rk.fakechat.R
import com.rk.fakechat.common.showToast
import com.rk.fakechat.common.viewBinding
import com.rk.fakechat.databinding.FragmentSignInBinding

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            login()
        }

        binding.goSignUp.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections.actionSignInToSignUp()
            )
        }
        initObservers()
    }

    private fun initObservers() {

        viewModel.result.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInToHome()
                )
            } else {
                requireContext().showToast("Hatalı e-mail veya şifre")
            }
        }

        viewModel.fail.observe(viewLifecycleOwner) {
            requireContext().showToast(it)
        }
    }

    private fun login() =
        viewModel.login(binding.signInEmail.text.toString(), binding.signInPassword.text.toString())
}