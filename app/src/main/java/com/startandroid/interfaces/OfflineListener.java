package com.startandroid.interfaces;

public interface OfflineListener {
    void onProcess();

    void onCompleted();

    void onFailed();

    void onCanceled();
}
