package com.startandroid.interfaces

interface OfflineListener {
    fun onProcess()
    fun onCompleted()
    fun onFailed()
    fun onCanceled()
}