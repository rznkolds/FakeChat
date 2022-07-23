package com.rkapp.fakechat.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayoutMediator
import com.rkapp.fakechat.adapter.PagerAdapter
import com.rkapp.fakechat.databinding.ActivityMainBinding
import com.rkapp.fakechat.viewmodels.MainViewModel

class Main : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.currentPicture().observe(this) {

            Glide.with(this).load(it).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.profile)
        }

        binding.pages.adapter = PagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.pages) { tab, position ->

            when (position) {

                0 -> {
                    tab.text = "FRIENDS"
                }

                1 -> {
                    tab.text = "SEARCH"
                }

                2 -> {
                    tab.text = "NOTIFICATION"
                }
            }

        }.attach()
    }
}