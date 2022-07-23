package com.rkapp.fakechat.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rkapp.fakechat.databinding.ActivitySplashBinding

class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser

        if (user != null) {

            startActivity(Intent(this, Main::class.java))

            finish()

        } else {

            object : CountDownTimer(2000, 1000) {

                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {

                    startActivity(Intent(this@Splash, Enter::class.java))

                    finish()
                }

            }.start()
        }
    }
}