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
import com.rkapp.fakechat.adapter.FriendsAdapter
import com.rkapp.fakechat.control.Connected
import com.rkapp.fakechat.databinding.FragmentFriendsBinding
import com.rkapp.fakechat.viewmodels.FriendsViewModel

class Friends : Fragment() {

	private val adapter by lazy { FriendsAdapter(this.requireContext()) }
	private lateinit var binding : FragmentFriendsBinding

	override fun onCreateView ( inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle? ) : View {

		binding = FragmentFriendsBinding.bind ( inflater.inflate ( R.layout.fragment_friends , container , false ) )

		val network = Connected ( ).network ( this.requireContext ( ) )

		if ( !network ) {

			startActivity ( Intent ( this.requireContext ( ) , Control::class.java ) )
		}

		binding.friends.layoutManager = LinearLayoutManager ( requireContext ( ) )

		binding.friends.adapter = adapter

		val vm = ViewModelProvider (this ) [ FriendsViewModel::class.java ]

		vm.user.observe ( this.viewLifecycleOwner ) {

			if ( it.isNotEmpty ( ) ) {

				adapter.setData ( it )
			}
		}

		return binding.root
	}
}