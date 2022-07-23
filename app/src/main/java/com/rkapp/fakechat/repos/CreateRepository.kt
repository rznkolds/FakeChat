package com.rkapp.fakechat.repos

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rkapp.fakechat.model.User

class CreateRepository {

	val result = MutableLiveData<Boolean> ( )
	private val cloud by lazy { Firebase.storage.reference }
	private val db by lazy { Firebase.database.reference }
	private val auth by lazy { Firebase.auth }

	fun createUser ( id : String , email : String , password : String , picture : Uri ) {

		auth.createUserWithEmailAndPassword ( email , password ).addOnSuccessListener {

			cloud.child ("images/${ auth.currentUser?.uid.toString ( ) }/profile/main").putFile ( picture ).addOnSuccessListener {

				cloud.child ("images/${ auth.currentUser?.uid.toString ( ) }/profile/main").downloadUrl.addOnSuccessListener {

					db.child ("Users" ).child ( auth.currentUser?.uid.toString ( ) ).setValue ( User ( id , email , auth.currentUser?.uid.toString ( ) , it.toString ( ) ) ).addOnSuccessListener {

						result.value = true
					}
				}
			}
		}
	}
}