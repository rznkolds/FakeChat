package com.rkapp.fakechat.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkapp.fakechat.repos.MainRepository

class MainViewModel : ViewModel() {

    private val mainRepo = MainRepository()

    fun currentPicture(): MutableLiveData<Uri> {

        return mainRepo.currentPicture()
    }
}