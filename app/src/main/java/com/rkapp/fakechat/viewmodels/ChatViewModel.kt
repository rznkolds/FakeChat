package com.rkapp.fakechat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkapp.fakechat.model.Message
import com.rkapp.fakechat.repos.ChatRepository

class ChatViewModel : ViewModel ( ) {

    var message = MutableLiveData < ArrayList < Message > > ( )
    private var chatRepo = ChatRepository ( )

    fun messageInfo ( sender : String ) {

        chatRepo.messageInfo ( sender )

        message = chatRepo.message
    }

    fun createUser ( sender : String , receiver : String , message : String ) {

        chatRepo.createMessage ( sender , receiver , message )
    }
}