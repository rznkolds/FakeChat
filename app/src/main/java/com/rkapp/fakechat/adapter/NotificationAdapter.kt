package com.rkapp.fakechat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rkapp.fakechat.databinding.WantItemBinding
import com.rkapp.fakechat.model.User
import com.rkapp.fakechat.utilies.UserDiffUtil

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.AdapterHolder>() {

    private var list = ArrayList < User > ( )
    private val db by lazy { Firebase.database.reference }
    private val auth by lazy { Firebase.auth }

    inner class AdapterHolder ( val binding: WantItemBinding ) : RecyclerView.ViewHolder ( binding.root )

    override fun onCreateViewHolder ( parent : ViewGroup , viewType: Int ) : AdapterHolder {

        return AdapterHolder ( WantItemBinding.inflate ( LayoutInflater.from ( parent.context ) , parent ,false ) )
    }

    override fun onBindViewHolder ( holder : AdapterHolder , position : Int ) {

        val current = list [ position ]

        holder.binding.name.text = current.id

        Glide.with ( holder.itemView ).load ( current.picture_url ).diskCacheStrategy ( DiskCacheStrategy.ALL ).into ( holder.binding.profile )

        holder.binding.accept.setOnClickListener { v ->

            db.child ("Users" ).child ( auth.uid.toString ( ) ).child ("Friends" ).child ( current.uid ).setValue ( User ( current.id , current.email , current.uid , current.picture_url ) )

            db.child ("Users").get ( ).addOnSuccessListener {

                for ( i in it.children ) {

                    if ( auth.uid.toString ( ) == i.child ("uid" ).value.toString ( ) ) {

                        val user = User (
                                            i.child ("id" ).value.toString ( ) ,
                                            i.child ("email" ).value.toString ( ) ,
                                            i.child ("uid" ).value.toString ( ) ,
                                            i.child ("picture_url" ).value.toString ( )
                                        )

                        db.child("Users").child ( current.uid ).child ("Friends" ).child ( auth.uid.toString ( ) ).setValue ( user )
                    }
                }
            }

            db.child ("Users" ).child ( auth.uid.toString ( ) ).child ("Notification" ).child ( current.uid ).removeValue ( )

            list.remove ( current )
            this.notifyItemRemoved ( position )
            this.notifyItemRangeRemoved ( position , list.size )
        }

        holder.binding.decline.setOnClickListener {

            db.child ("Users" ).child ( auth.uid.toString ( ) ).child ("Notification" ).child ( current.uid ).removeValue ( )

            list.remove ( current )
            this.notifyItemRemoved ( position )
            this.notifyItemRangeRemoved ( position , list.size )
        }
    }

    override fun getItemCount ( ) : Int {

        return list.size
    }

    fun setData ( new_user_list : ArrayList < User > ) {

        val result = DiffUtil.calculateDiff ( UserDiffUtil ( list , new_user_list ) )

        list = new_user_list

        result.dispatchUpdatesTo (this )
    }
}