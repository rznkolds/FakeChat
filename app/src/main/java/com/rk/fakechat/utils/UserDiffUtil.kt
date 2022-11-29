package com.rk.fakechat.utils

import androidx.recyclerview.widget.DiffUtil
import com.rk.fakechat.data.model.User

class UserDiffUtil(
    private val oldList: ArrayList<User>,
    private val newList: ArrayList<User>
): DiffUtil.Callback() {

    override fun areItemsTheSame(old: Int, new: Int) = (oldList[old].uid == newList[new].uid)

    override fun areContentsTheSame(old: Int, new: Int) = oldList[old] == newList[new]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size
}