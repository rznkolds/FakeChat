package com.rkapp.fakechat.utilies

import androidx.recyclerview.widget.DiffUtil
import com.rkapp.fakechat.model.Message

class ChatDiffUtil(private val old_list: List<Message>, private val new_list: ArrayList<Message>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return old_list[oldItemPosition].sender == new_list[newItemPosition].sender
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return when {

            old_list[oldItemPosition].message != new_list[newItemPosition].message -> {

                false
            }

            old_list[oldItemPosition].sender != new_list[newItemPosition].sender -> {

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