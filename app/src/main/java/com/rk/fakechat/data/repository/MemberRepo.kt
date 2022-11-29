package com.rk.fakechat.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.rk.fakechat.data.model.Message
import com.rk.fakechat.data.model.User

class MemberRepo {

    private val cloud by lazy { Firebase.storage.reference }
    private val db by lazy { Firebase.database.reference }
    private val auth by lazy { Firebase.auth }
    val result = MutableLiveData<Boolean>()
    var message = MutableLiveData<ArrayList<Message>>()
    var user = MutableLiveData<ArrayList<User>>()
    val picture = MutableLiveData<Uri>()

    // User login for Sign In fragment

    fun login(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            result.value = it.isSuccessful
        }
    }

    // User register for Sign Up fragment

    fun register(id: String, email: String, password: String, picture: Uri) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->

            if (it.isSuccessful) {

                cloud.child("images/${auth.currentUser?.uid.toString()}/profile/main")
                    .putFile(picture).addOnSuccessListener {

                        it.metadata?.reference?.downloadUrl?.addOnSuccessListener { p ->

                            val user =
                                User(id, email, auth.currentUser?.uid.toString(), p.toString())

                            db.child("Users").child(auth.currentUser?.uid.toString()).setValue(user)
                                .addOnSuccessListener {
                                    result.value = true
                                }
                        }
                    }
            } else {
                result.value = false
            }
        }
    }

    // User profile picture for Home fragment

    fun getPicture() {

        cloud.child("images/${auth.uid.toString()}/profile/main").downloadUrl.addOnSuccessListener {
            picture.value = it
        }
    }

    // Retrieval of friends list for Friends fragment

    fun getFriends() {

        val temp = ArrayList<User>()

        db.child("Users").child(auth.currentUser?.uid.toString()).child("Friends").get()
            .addOnSuccessListener {

                for (i in it.children) {

                    if (i.key.toString() != auth.uid.toString()) {

                        temp.add(
                            User(
                                i.child("id").value.toString(),
                                i.child("email").value.toString(),
                                i.child("uid").value.toString(),
                                i.child("picture_url").value.toString()
                            )
                        )
                    }
                }
                user.value = temp
            }
    }

    // Searching and adding users for Search fragment

    fun getSearch(id: String) {

        val temp = ArrayList<User>()

        db.child("Users").get().addOnSuccessListener {

            for (i in it.children) {

                if (i.key.toString() != auth.uid.toString()) {

                    if (id == i.child("id").value.toString()) {

                        temp.add(
                            User(
                                i.child("id").value.toString(),
                                i.child("email").value.toString(),
                                i.child("uid").value.toString(),
                                i.child("picture_url").value.toString()
                            )
                        )
                    }
                }
            }
            user.value = temp
        }
    }

    fun add(user: User) {

        db.child("Users").get().addOnSuccessListener {

            for (i in it.children) {

                if (auth.uid.toString() == i.child("uid").value.toString()) {

                    val model = User(
                        i.child("id").value.toString(),
                        i.child("email").value.toString(),
                        i.child("uid").value.toString(),
                        i.child("picture_url").value.toString(),
                    )

                    db.child("Users").child(user.uid).child("Notification")
                        .child(auth.uid.toString()).setValue(model).addOnSuccessListener {
                            result.value = true
                        }
                }
            }
        }
    }

    // Receiving, accepting and rejecting requests for Notification fragment

    fun getWant() {

        val temp = ArrayList<User>()

        db.child("Users").child(auth.uid.toString()).child("Notification").get()
            .addOnSuccessListener {

                for (i in it.children) {
                    temp.add(
                        User(
                            i.child("id").value.toString(),
                            i.child("email").value.toString(),
                            i.child("uid").value.toString(),
                            i.child("picture_url").value.toString()
                        )
                    )
                }
                user.value = temp
            }
    }

    fun accept(user: User) {

        db.child("Users").child(auth.uid.toString()).child("Friends").child(user.uid)
            .setValue(user)

        db.child("Users").get().addOnSuccessListener {

            for (i in it.children) {

                if (auth.uid.toString() == i.child("uid").value.toString()) {

                    val model = User(
                        i.child("id").value.toString(),
                        i.child("email").value.toString(),
                        i.child("uid").value.toString(),
                        i.child("picture_url").value.toString()
                    )

                    db.child("Users").child(user.uid).child("Friends")
                        .child(auth.uid.toString()).setValue(model)
                }
            }
        }

        db.child("Users").child(auth.uid.toString()).child("Notification")
            .child(user.uid)
            .removeValue()
    }

    fun decline(user: User) {

        Log.d("uid: ", user.uid)

        db.child("Users").child(auth.uid.toString()).child("Notification")
            .child(user.uid)
            .removeValue()
    }

    // Receive and save messages for Chat fragment

    fun getMessage(sender: String) {

        val temp = ArrayList<Message>()

        db.child("Chats").child(sender).child("messages")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    for (i in snapshot.children) {
                        temp.add(
                            Message(
                                i.child("message").value.toString(),
                                i.child("sender").value.toString()
                            )
                        )
                    }
                    message.value = temp
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun sendMessage(sender: String, receiver: String, msg: String) {

        val message = Message(msg, auth.uid.toString())

        db.child("Chats").child(sender).child("messages").push().setValue(message)
            .addOnSuccessListener {

                db.child("Chats").child(receiver).child("messages").push().setValue(message)
            }
    }
}
