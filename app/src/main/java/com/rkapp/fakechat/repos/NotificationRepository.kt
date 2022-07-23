package com.rkapp.fakechat.repos

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rkapp.fakechat.model.User

class NotificationRepository {

	val user = MutableLiveData<ArrayList<User>>()
	private val db by lazy { Firebase.database.reference }
	private val auth by lazy { Firebase.auth }

	fun wantInfo ( ) {

		val temp = ArrayList<User>()

		db.child("Users").child(auth.uid.toString()).child("Notification").get().addOnSuccessListener {

			for ( i in it.children ) {

				temp.add(
					User(
						i.child("id").value.toString() ,
						i.child("email").value.toString() ,
						i.child("uid").value.toString() ,
						i.child("picture_url").value.toString()
					)
				)
			}
			user.value = temp
		}
	}
}