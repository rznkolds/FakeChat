package com.rk.fakechat.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.rk.fakechat.R
import com.rk.fakechat.common.setPicture
import com.rk.fakechat.common.viewBinding
import com.rk.fakechat.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homePages.adapter = PagerAdapter(this)

        TabLayoutMediator(binding.homeTabs, binding.homePages) { tab, position ->

            when (position) {

                0 -> tab.text = "FRIENDS"

                1 -> tab.text = "SEARCH"

                2 -> tab.text = "NOTIFICATION"
            }

        }.attach()

        initObservers()
    }

    private fun initObservers() = viewModel.picture.observe(viewLifecycleOwner) {

        binding.homeProfile.setPicture(it.toString())
    }
}