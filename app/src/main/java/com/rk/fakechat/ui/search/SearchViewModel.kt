package com.rk.fakechat.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rk.fakechat.data.model.User
import com.rk.fakechat.data.repository.MemberRepo

class SearchViewModel : ViewModel() {

    private var memberRepo = MemberRepo()

    private var _user = MutableLiveData<ArrayList<User>>()
    val user: LiveData<ArrayList<User>>
        get() = _user

    init {
        _user = memberRepo.user
    }

    fun search(id: String) = memberRepo.getSearch(id)

    fun add(user: User) = memberRepo.add(user)
}