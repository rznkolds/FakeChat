package com.rkapp.fakechat.repos

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EnterRepository {

    private val auth by lazy { Firebase.auth }

    fun login(email: String, password: String): MutableLiveData<Boolean> {

        val result = MutableLiveData<Boolean>()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            if (it.isSuccessful) {

                result.value = true
            }
        }

        return result
    }
}