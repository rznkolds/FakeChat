package com.rk.fakechat.ui.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rk.fakechat.common.setPicture
import com.rk.fakechat.data.model.User
import com.rk.fakechat.databinding.FriendItemBinding
import com.rk.fakechat.utils.UserDiffUtil

class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.AdapterHolder>() {

    private var list = ArrayList<User>()
    var onFriendClick: (User) -> Unit = {}

    inner class AdapterHolder(val binding: FriendItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {

            friendProfile.setPicture(user.picture_url)
            friendName.text = user.id

            itemView.setOnClickListener {
                onFriendClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder = AdapterHolder(

        FriendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    fun setData(new_user_list: ArrayList<User>) {
        list.clear()
        val result = DiffUtil.calculateDiff(UserDiffUtil(list, new_user_list))
        list.addAll(new_user_list)
        result.dispatchUpdatesTo(this)
    }
}