package com.rkapp.fakechat.repos

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rkapp.fakechat.model.User

class SearchRepository {

    val user = MutableLiveData < ArrayList < User > > ( )
    private val db by lazy { Firebase.database.reference }
    private val auth by lazy { Firebase.auth }

    fun searchUser ( id: String ) {

        val temp = ArrayList < User > ( )

        db.child ("Users" ).get ( ).addOnSuccessListener {

            for ( i in it.children ) {

                if ( i.key.toString ( ) != auth.uid.toString() ) {

                    if ( id == i.child ("id" ).value.toString ( ) ) {

                        temp.add (
                            User (
                                i.child ("id" ).value.toString ( ) ,
                                i.child ("email" ).value.toString ( ) ,
                                i.child ("uid" ).value.toString ( ) ,
                                i.child ("picture_url" ).value.toString ( )
                            )
                        )
                    }
                }
            }

            user.value = temp
        }
    }
}