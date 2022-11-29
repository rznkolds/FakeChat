package com.rk.fakechat.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rk.fakechat.ui.friends.FriendsFragment
import com.rk.fakechat.ui.notification.NotificationFragment
import com.rk.fakechat.ui.search.SearchFragment

class PagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {

        0 -> FriendsFragment()

        1 -> SearchFragment()

        2 -> NotificationFragment()

        else -> FriendsFragment()
    }
}