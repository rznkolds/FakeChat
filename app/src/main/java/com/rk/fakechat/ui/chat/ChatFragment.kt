package com.rk.fakechat.ui.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rk.fakechat.R
import com.rk.fakechat.common.setPicture
import com.rk.fakechat.common.viewBinding
import com.rk.fakechat.databinding.FragmentChatBinding


class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val adapter by lazy { ChatAdapter() }
    private val binding by viewBinding(FragmentChatBinding::bind)
    private val auth by lazy { Firebase.auth.currentUser?.uid.toString() }
    private val viewModel: ChatViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id")
        val uid = arguments?.getString("uid")
        val picture = arguments?.getString("picture")

        with(binding){

            chatProfile.setPicture(picture.toString())
            chatName.text = id

            send.setOnClickListener {
                val sender = uid + auth
                val receiver = auth + uid

                viewModel.sendMessage(sender , receiver , message.text.toString ( ))

                message.text.clear()
            }
        }

        initObservers()
    }

    private fun initObservers() = with(binding) {

        viewModel.message.observe(viewLifecycleOwner) {
            chatList.layoutManager = LinearLayoutManager(requireContext()).apply {
                this.stackFromEnd = true
            }
            chatList.adapter = adapter
            adapter.setData(it)
        }
    }
}