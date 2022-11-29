package com.rk.fakechat.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.rk.fakechat.databinding.ReceiverItemBinding
import com.rk.fakechat.databinding.SendItemBinding
import com.rk.fakechat.data.model.Message
import com.rk.fakechat.utils.ChatDiffUtil

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val auth = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private var list = ArrayList<Message>()

    inner class SendHolder(val binding: SendItemBinding): RecyclerView.ViewHolder(binding.root)

    inner class ReceiverHolder(val binding: ReceiverItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == 1) {

            SendHolder(
                SendItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        } else {

            ReceiverHolder(
                ReceiverItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val current = list[position]

        if (holder.javaClass == SendHolder::class.java) {

            val view = holder as SendHolder
            view.binding.sender.text = current.message

        } else {

            val view = holder as ReceiverHolder
            view.binding.receiver.text = current.message
        }
    }

    fun setData(new_user_list: ArrayList<Message>) {
        list.clear()
        val result = DiffUtil.calculateDiff(ChatDiffUtil(list, new_user_list))
        list = new_user_list
        result.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int = if (list[position].sender == auth) 1 else 2

    override fun getItemCount(): Int = list.size
}