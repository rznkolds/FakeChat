package com.rkapp.fakechat.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkapp.fakechat.repos.CreateRepository

class CreateViewModel : ViewModel ( ) {

    var result = MutableLiveData<Boolean> ( )
    private var createRepo = CreateRepository ( )

    fun createUser ( id: String , email: String , password: String , picture : Uri ) {

        createRepo.createUser ( id , email , password, picture )

        result = createRepo.result
    }
}