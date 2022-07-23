package com.rkapp.fakechat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkapp.fakechat.model.User
import com.rkapp.fakechat.repos.NotificationRepository

class NotificationViewModel : ViewModel() {

	var user = MutableLiveData<ArrayList<User>>()
	private val notificationRepo = NotificationRepository ( )

	init {
		wantInfo ( )
	}

	private fun wantInfo ( ) {

		notificationRepo.wantInfo ( )

		user = notificationRepo.user
	}
}