package com.rkapp.fakechat.utilies

import androidx.recyclerview.widget.DiffUtil
import com.rkapp.fakechat.model.User

class UserDiffUtil(private val old_list: ArrayList<User>, private val new_list: ArrayList<User>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(old: Int, new: Int): Boolean {

        return (old_list[old].id == new_list[new].id)
    }

    override fun areContentsTheSame(old: Int, new: Int): Boolean {

        return when {

            old_list[old].id != new_list[new].id -> {

                false
            }

            old_list[old].email != new_list[new].email -> {

                false
            }

            old_list[old].uid != new_list[new].uid -> {

                false
            }

            old_list[old].picture_url != new_list[new].picture_url -> {

                false
            }

            else -> true
        }
    }

    override fun getOldListSize(): Int {

        return old_list.size
    }

    override fun getNewListSize(): Int {

        return new_list.size
    }
}