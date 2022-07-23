package com.rkapp.fakechat.pieces

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkapp.fakechat.R
import com.rkapp.fakechat.activities.Control
import com.rkapp.fakechat.adapter.NotificationAdapter
import com.rkapp.fakechat.control.Connected
import com.rkapp.fakechat.databinding.FragmentNotificationBinding
import com.rkapp.fakechat.viewmodels.NotificationViewModel

class Notification : Fragment ( ) {

	private lateinit var binding : FragmentNotificationBinding
	private val adapter by lazy { NotificationAdapter ( ) }

	override fun onCreateView ( inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle? ) : View {

		binding = FragmentNotificationBinding.bind ( inflater.inflate ( R.layout.fragment_notification , container , false ) )

		val network = Connected ( ).network ( this.requireContext ( ) )

		if ( !network ) {

			startActivity ( Intent ( this.requireContext ( ) , Control::class.java ) )
		}

		binding.wants.layoutManager = LinearLayoutManager ( requireContext ( ) )

		binding.wants.adapter = adapter

		val viewModel = ViewModelProvider (this ) [ NotificationViewModel::class.java ]

		viewModel.user.observe ( this.viewLifecycleOwner ) {

			if ( it.isNotEmpty ( ) ) {

				adapter.setData ( it )
			}
		}

		return binding.root
	}
}