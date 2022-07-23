package com.rkapp.fakechat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkapp.fakechat.model.User
import com.rkapp.fakechat.repos.FriendsRepository

class FriendsViewModel : ViewModel() {

    var user = MutableLiveData<ArrayList<User>>()
    private var enterRepo = FriendsRepository ( )

    init {
        friendsInfo ( )
    }

    private fun friendsInfo ( ) {

        enterRepo.friendsInfo ( )

        user = enterRepo.user
    }
}