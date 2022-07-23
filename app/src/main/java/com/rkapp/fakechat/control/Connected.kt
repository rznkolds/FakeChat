package com.rkapp.fakechat.control

import android.content.Context
import android.net.ConnectivityManager

class Connected {

	fun network ( context : Context ) : Boolean {

			val connectivityManager = context.getSystemService ( Context.CONNECTIVITY_SERVICE ) as ConnectivityManager

			val active = connectivityManager.activeNetwork

			return active != null
	}
}