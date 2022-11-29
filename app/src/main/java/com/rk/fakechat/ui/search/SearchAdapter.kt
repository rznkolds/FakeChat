package com.rk.fakechat.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rk.fakechat.common.setPicture
import com.rk.fakechat.databinding.SearchItemBinding
import com.rk.fakechat.data.model.User
import com.rk.fakechat.utils.UserDiffUtil

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.AdapterHolder>() {

    private var list = ArrayList<User>()
    var onSearchClick: (User) -> Unit = {}

    inner class AdapterHolder(val binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {

            searchName.text = user.id
            searchProfile.setPicture(user.picture_url)

            add.setOnClickListener {
                onSearchClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder {

        return AdapterHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
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