package com.rkapp.fakechat.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rkapp.fakechat.databinding.ActivityEnterBinding
import com.rkapp.fakechat.viewmodels.EnterViewModel

class Enter : AppCompatActivity() {

    private lateinit var binding: ActivityEnterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {

            login()
        }

        binding.register.setOnClickListener {

            startActivity(Intent(this, Create::class.java))
        }
    }

    private fun login() {

        val viewModel = ViewModelProvider(this)[EnterViewModel::class.java]

        viewModel.loginUser(binding.email.text.toString(), binding.password.text.toString())
            .observe(this) {

                if (it == true) {

                    startActivity(Intent(this@Enter, Main::class.java))

                    finish()

                } else {

                    Toast.makeText(this@Enter, "Hatalı E-mail veya Şifre", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}