package com.rkapp.fakechat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rkapp.fakechat.databinding.SearchItemBinding
import com.rkapp.fakechat.model.User
import com.rkapp.fakechat.utilies.UserDiffUtil
import com.rkapp.fakechat.viewmodels.SearchViewModel

class SearchAdapter ( var context : ViewModelStoreOwner , var owner : LifecycleOwner ) : RecyclerView.Adapter<SearchAdapter.AdapterHolder>() {

    private val db by lazy { Firebase.database.reference }
    private val auth by lazy { Firebase.auth }
    private var list = ArrayList<User>()

    inner class AdapterHolder ( val binding: SearchItemBinding ) : RecyclerView.ViewHolder ( binding.root )

    override fun onCreateViewHolder ( parent : ViewGroup , viewType : Int ) : AdapterHolder {

        return AdapterHolder ( SearchItemBinding.inflate ( LayoutInflater.from ( parent.context ) , parent , false ) )
    }

    override fun onBindViewHolder ( holder: AdapterHolder, position: Int ) {

        val current = list [ position ]

        holder.binding.searchName.text = current.id

        Glide.with ( holder.itemView.context ).load ( current.picture_url ).diskCacheStrategy ( DiskCacheStrategy.ALL ).into ( holder.binding.searchProfile )

        holder.binding.add.setOnClickListener { v ->

            db.child ("Users" ).get ( ).addOnSuccessListener {

                for ( i in it.children ) {

                    if ( auth.uid.toString ( ) == i.child("uid").value.toString ( ) ) {

                        val user = User (
                            i.child ("id" ).value.toString ( ) ,
                            i.child ("email" ).value.toString ( ) ,
                            i.child ("uid" ).value.toString ( ) ,
                            i.child ("picture_url" ).value.toString ( ) ,
                        )

                        db.child ("Users" ).child ( current.uid ).child ("Notification" ).child ( auth.uid.toString ( ) ).setValue ( user )

                        list.remove ( current )
                        this.notifyItemRemoved ( position )
                        this.notifyItemRangeRemoved ( position , list.size )
                    }
                }
            }
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