package com.rk.fakechat.ui.splash

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rk.fakechat.R
import com.rk.fakechat.common.checkNetwork

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

            val builder = AlertDialog.Builder(this.requireContext()).apply {
                this.setView(layoutInflater.inflate(R.layout.control_dialog, null))
                this.setCancelable(false)
            }

            builder.create().also {
                it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.window?.setLayout(200, 200)
                it.show()
            }
        }
    }
}