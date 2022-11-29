package com.rk.fakechat.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.fakechat.data.model.User
import com.rk.fakechat.data.repository.MemberRepo

class NotificationViewModel : ViewModel() {

    private var memberRepo = MemberRepo()

    private var _user = MutableLiveData<ArrayList<User>>()
    val user: LiveData<ArrayList<User>>
        get() = _user

    init {
        getWant()

        _user = memberRepo.user
    }

    private fun getWant() = memberRepo.getWant()

    fun accept(user: User) = memberRepo.accept(user)

    fun decline(user: User) = memberRepo.decline(user)
}