package com.aungbophyoe.data.utility

import android.content.Context
import android.net.ConnectivityManager
import android.view.View

sealed class LDEFlow<out D: Any> {
    data class Data<out G: Any>(val data: G): LDEFlow<G>()
    data class Error(val message: String,val cause: Throwable? = null): LDEFlow<Nothing>()
    object Loading: LDEFlow<Nothing>()
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}