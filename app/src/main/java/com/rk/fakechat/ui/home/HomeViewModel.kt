package com.rk.fakechat.ui.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.fakechat.data.repository.MemberRepo

class HomeViewModel : ViewModel() {

    private val memberRepo = MemberRepo()

    private var _picture = MutableLiveData<Uri>()
    val picture: LiveData<Uri>
        get() = _picture

    init {
        getPicture()

        _picture = memberRepo.picture
    }

    private fun getPicture() = memberRepo.getPicture()
}