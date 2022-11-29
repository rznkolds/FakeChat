package com.rk.fakechat.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rk.fakechat.common.setPicture
import com.rk.fakechat.databinding.WantItemBinding
import com.rk.fakechat.data.model.User
import com.rk.fakechat.utils.UserDiffUtil

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.AdapterHolder>() {

    private var list = ArrayList<User>()
    var onAcceptClick: (User) -> Unit = {}
    var onDeclineClick: (User) -> Unit = {}

    inner class AdapterHolder(val binding: WantItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {

            notificationName.text = user.id
            notificationProfile.setPicture(user.picture_url)

            accept.setOnClickListener {
                onAcceptClick(user)
            }

            decline.setOnClickListener {
                onDeclineClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {
        return AdapterHolder(
            WantItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    fun setData(new_user_list: ArrayList<User>) {
        list.clear()
        val result = DiffUtil.calculateDiff(UserDiffUtil(list, new_user_list))
        list = new_user_list
        result.dispatchUpdatesTo(this)
    }
}