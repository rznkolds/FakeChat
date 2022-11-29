package com.rk.fakechat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rk.fakechat.common.viewBinding
import com.rk.fakechat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}