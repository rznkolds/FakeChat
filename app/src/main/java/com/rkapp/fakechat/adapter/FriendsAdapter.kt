package com.rkapp.fakechat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rkapp.fakechat.activities.Chat
import com.rkapp.fakechat.databinding.FriendItemBinding
import com.rkapp.fakechat.eventbus.UserInfo
import com.rkapp.fakechat.model.User
import com.rkapp.fakechat.utilies.UserDiffUtil
import org.greenrobot.eventbus.EventBus

class FriendsAdapter(private val context: Context) :
    RecyclerView.Adapter<FriendsAdapter.AdapterHolder>() {

    private var list = ArrayList<User>()

    inner class AdapterHolder(val binding: FriendItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {

        return AdapterHolder(
            FriendItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {

        val current = list[position]

        Glide.with(holder.itemView).load(current.picture_url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.binding.profile)

        holder.binding.name.text = current.id

        holder.itemView.setOnClickListener {

            EventBus.getDefault ( ).postSticky ( UserInfo ( current.id, current.email, current.uid, current.picture_url ) )

            context.startActivity ( Intent ( context , Chat::class.java ) )
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }

    fun setData(new_user_list: ArrayList<User>) {

        val result = DiffUtil.calculateDiff(UserDiffUtil(list, new_user_list))

        list.addAll(new_user_list)

        result.dispatchUpdatesTo(this)
    }
}