package com.startandroid.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.startandroid.App.Companion.getContext

object Utils {
    @JvmStatic
    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager =
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val networkCapabilities = connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)
            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }
}
