package com.rk.fakechat.ui.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rk.fakechat.R
import com.rk.fakechat.common.viewBinding
import com.rk.fakechat.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private val binding by viewBinding(FragmentNotificationBinding::bind)
    private val viewModel: NotificationViewModel by viewModels()
    private val adapter by lazy { NotificationAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.onAcceptClick = {
            viewModel.accept(it)
        }

        adapter.onDeclineClick = {
            viewModel.decline(it)
        }

        initObservers()
    }

    private fun initObservers() = viewModel.user.observe(viewLifecycleOwner) {
        binding.wants.layoutManager = LinearLayoutManager(requireContext())
        binding.wants.adapter = adapter
        adapter.setData(it)
    }
}