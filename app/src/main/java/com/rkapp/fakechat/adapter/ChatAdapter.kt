package com.rkapp.fakechat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.rkapp.fakechat.databinding.ReceiverItemBinding
import com.rkapp.fakechat.databinding.SendItemBinding
import com.rkapp.fakechat.model.Message
import com.rkapp.fakechat.utilies.ChatDiffUtil

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val auth = FirebaseAuth.getInstance().currentUser?.uid.toString()

    private var list = emptyList<Message>()

    inner class SendHolder(val binding: SendItemBinding) : RecyclerView.ViewHolder(binding.root)

    inner class ReceiverHolder(val binding: ReceiverItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == 1) {

            SendHolder(SendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

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

        val result = DiffUtil.calculateDiff(ChatDiffUtil(list, new_user_list))

        list = new_user_list

        result.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {

        val current = list[position]

        return if (current.sender == auth) {

            1

        } else {

            2
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }
}