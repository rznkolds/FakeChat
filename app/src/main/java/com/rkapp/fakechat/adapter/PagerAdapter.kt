package com.rkapp.fakechat.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rkapp.fakechat.pieces.Friends
import com.rkapp.fakechat.pieces.Notification
import com.rkapp.fakechat.pieces.Search

class PagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {

        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> {

                Friends()
            }

            1 -> {

                Search()
            }

            2 -> {

                Notification()
            }

            else -> {

                Friends()
            }
        }
    }
}