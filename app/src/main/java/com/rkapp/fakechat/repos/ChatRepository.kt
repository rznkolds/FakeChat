package com.rkapp.fakechat.repos

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rkapp.fakechat.model.Message
import com.rkapp.fakechat.model.User

class ChatRepository {

    var message = MutableLiveData<ArrayList<Message>>()
    private val db by lazy { Firebase.database.reference }
    private val auth by lazy { Firebase.auth }

    fun messageInfo(sender: String) {

        val temp = ArrayList < Message > ( )

        db.child ("Chats" ).child ( sender ).child ("messages" ).addValueEventListener ( object : ValueEventListener {

                override fun onDataChange ( snapshot : DataSnapshot ) {

                    temp.clear ( )

                    for ( i in snapshot.children ) {

                        temp.add (
                            Message (
                                i.child ("message" ).value.toString ( ) ,
                                i.child ("sender" ).value.toString ( )
                            )
                        )
                    }

                    message.value = temp
                }

                override fun onCancelled ( error : DatabaseError ) { }
        })
    }

    fun createMessage ( sender : String , receiver : String , msg : String ) {

        val message = Message ( msg , auth.uid.toString ( ) )

        db.child ("Chats" ).child ( sender ).child ("messages" ).push ( ).setValue ( message ).addOnSuccessListener {

                db.child ("Chats" ).child ( receiver ).child ("messages" ).push ( ).setValue ( message )
            }
    }
}