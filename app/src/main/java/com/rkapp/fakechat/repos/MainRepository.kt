package com.rkapp.fakechat.repos

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainRepository {

    private val cloud by lazy { Firebase.storage.reference }
    private val auth by lazy { Firebase.auth }

    fun currentPicture ( ): MutableLiveData<Uri > {

        val result = MutableLiveData<Uri>()

        cloud.child("images/${auth.uid.toString()}/profile/main").downloadUrl.addOnSuccessListener {

            result.value = it
        }

        return result
    }
}