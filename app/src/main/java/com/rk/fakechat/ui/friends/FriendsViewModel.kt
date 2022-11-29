package com.rk.fakechat.ui.friends

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.fakechat.data.model.User
import com.rk.fakechat.data.repository.MemberRepo

class FriendsViewModel: ViewModel() {

    private var memberRepo = MemberRepo()

    private var _user = MutableLiveData<ArrayList<User>>()
    val user: LiveData<ArrayList<User>>
        get() = _user

    init {
        getFriends()

        _user = memberRepo.user
    }

    private fun getFriends() = memberRepo.getFriends()
}