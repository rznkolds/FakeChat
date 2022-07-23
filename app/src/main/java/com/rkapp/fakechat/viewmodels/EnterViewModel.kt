package com.rkapp.fakechat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkapp.fakechat.repos.EnterRepository

class EnterViewModel : ViewModel() {

    private var enterRepo = EnterRepository()

    fun loginUser(email: String, password: String): MutableLiveData<Boolean> {

        return enterRepo.login(email, password)
    }
}