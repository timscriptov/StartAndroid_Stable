package com.startandroid.utils;

import com.google.android.gms.ads.AdListener;

public class InsAdListener extends AdListener {
    @Override
    public void onAdClosed() {
        super.onAdClosed();
        Ads.loadInsAd();
    }
}