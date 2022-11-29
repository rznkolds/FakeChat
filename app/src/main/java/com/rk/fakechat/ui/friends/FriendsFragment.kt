package com.rk.fakechat.ui.friends


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rk.fakechat.R
import com.rk.fakechat.common.viewBinding
import com.rk.fakechat.databinding.FragmentFriendsBinding


class FriendsFragment : Fragment(R.layout.fragment_friends) {

    private val binding by viewBinding(FragmentFriendsBinding::bind)
    private val viewModel: FriendsViewModel by viewModels()
    private val adapter by lazy { FriendsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.onFriendClick = {

            val bundle = Bundle().apply {
                putString("id", it.id)
                putString("uid", it.uid)
                putString("picture", it.picture_url)
            }

            parentFragment?.findNavController()?.navigate(
                R.id.action_home_to_chat,
                bundle
            )
        }

        initObservers()
    }

    private fun initObservers() = with(binding) {

        viewModel.user.observe(viewLifecycleOwner) {
            friends.layoutManager = LinearLayoutManager(requireContext())
            friends.adapter = adapter
            adapter.setData(it)
        }
    }
}