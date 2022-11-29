package com.rk.fakechat.ui.signup

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.fakechat.data.repository.MemberRepo

class SignUpViewModel : ViewModel() {

    private var memberRepo = MemberRepo()

    private var _result = MutableLiveData<Boolean>()
    val result: LiveData<Boolean>
        get() = _result

    private var _fail = MutableLiveData<String>()
    val fail: LiveData<String>
        get() = _fail

    init {
        _result = memberRepo.result
    }

    fun register(id: String, email: String, password: String, picture: Uri?) {

        val isValid = when {
            id.isEmpty() -> false
            email.isEmpty() -> false
            password.isEmpty() -> false
            else -> true
        }

        if (isValid && picture != null) {
            memberRepo.register(id, email, password, picture)
        } else if (picture == null) {
            _fail.value = "Bir resim seçin"
        } else {
            _fail.value = "Boş kısımları belirtin"
        }
    }
}