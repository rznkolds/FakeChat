package com.rk.fakechat.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rk.fakechat.data.model.Message
import com.rk.fakechat.data.model.User
import com.rk.fakechat.data.repository.MemberRepo

class ChatViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val auth by lazy { Firebase.auth.currentUser?.uid.toString() }
    private var memberRepo = MemberRepo()

    private var _message = MutableLiveData<ArrayList<Message>>()
    val message: LiveData<ArrayList<Message>>
        get() = _message

    init {
        savedStateHandle.get<String>("uid")?.let {
            val sender = it + auth
            getMessage(sender)
        }

        _message = memberRepo.message
    }

    fun sendMessage(sender: String, receiver: String, message: String) = memberRepo.sendMessage(sender, receiver, message)

    private fun getMessage(sender: String) = memberRepo.getMessage(sender)
}