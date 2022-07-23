package com.rkapp.fakechat.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkapp.fakechat.model.User
import com.rkapp.fakechat.repos.SearchRepository

class SearchViewModel : ViewModel ( ) {

    var user = MutableLiveData < ArrayList < User > > ( )

    private val searchRepo = SearchRepository ( )

    fun searchUser ( id: String ) {

        searchRepo.searchUser ( id )

        user = searchRepo.user
    }
}