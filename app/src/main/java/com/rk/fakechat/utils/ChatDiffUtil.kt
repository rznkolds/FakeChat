package com.rk.fakechat.utils

import androidx.recyclerview.widget.DiffUtil
import com.rk.fakechat.data.model.Message

class ChatDiffUtil(
    private val oldList: ArrayList<Message>,
    private val newList: ArrayList<Message>
): DiffUtil.Callback() {

    override fun areItemsTheSame(old: Int, new: Int) = (oldList[old].sender == newList[new].sender)

    override fun areContentsTheSame(old: Int, new: Int) = oldList[old] == newList[new]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size
}