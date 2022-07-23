package com.rkapp.fakechat.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rkapp.fakechat.adapter.ChatAdapter
import com.rkapp.fakechat.databinding.ActivityChatBinding
import com.rkapp.fakechat.eventbus.UserInfo
import com.rkapp.fakechat.viewmodels.ChatViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class Chat : AppCompatActivity ( ) {

    private val adapter by lazy { ChatAdapter ( ) }
    private lateinit var binding : ActivityChatBinding
    private val auth by lazy { Firebase.auth.currentUser?.uid.toString ( ) }

    override fun onCreate ( savedInstanceState: Bundle? ) {
        super.onCreate ( savedInstanceState )

        binding = ActivityChatBinding.inflate ( layoutInflater )

        setContentView ( binding.root )

        EventBus.getDefault ( ).register (this )

        binding.chat.layoutManager = LinearLayoutManager (this ).apply {

            this.stackFromEnd = true
        }

        binding.chat.adapter = adapter
    }

    @Subscribe ( sticky = true )
    fun info ( event : UserInfo ) {

        Glide.with (this ).load ( event.picture ).diskCacheStrategy ( DiskCacheStrategy.ALL ).into ( binding.chatProfile )

        binding.chatName.text = event.id

        val sender = event.uid + auth

        val receiver = auth + event.uid

        val vm = ViewModelProvider (this ) [ ChatViewModel::class.java]

        vm.messageInfo ( sender )

        vm.message.observe (this ) {

            adapter.setData ( it )
        }

        binding.sendMsg.setOnClickListener {

            vm.createUser ( sender , receiver , binding.txtMsg.text.toString ( ) )

            binding.txtMsg.setText ( "" )
        }
    }

    override fun onDestroy ( ) {
        super.onDestroy ( )

        EventBus.getDefault ( ).unregister (this )
    }
}