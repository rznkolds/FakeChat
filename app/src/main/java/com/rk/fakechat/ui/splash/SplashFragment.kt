package com.rk.fakechat.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rk.fakechat.R
import com.rk.fakechat.common.checkNetwork
import com.rk.fakechat.common.showToast

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val auth by lazy { Firebase.auth.currentUser }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().checkNetwork()) {

            object : CountDownTimer(1000, 1000) {

                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {

                    if (auth?.uid != null) {
                        findNavController().navigate(
                            SplashFragmentDirections.actionSplashToHome()
                        )
                    } else {
                        findNavController().navigate(
                            SplashFragmentDirections.actionSplashToSignIn()
                        )
                    }
                }
            }.start()

        } else {

            requireContext().showToast("İnternet bulunamadı")
        }
    }
}