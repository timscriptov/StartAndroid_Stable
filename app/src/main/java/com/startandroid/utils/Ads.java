package com.startandroid.utils;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.startandroid.App;
import com.startandroid.data.Constants;

import org.jetbrains.annotations.NotNull;

public class Ads extends AdListener {

    public static @NotNull AdView getBanner() {
        AdView adView = new AdView(App.getContext());
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(Constants.bannerId);
        adView.setAdListener(new Ads());
        adView.loadAd(new AdRequest.Builder().build());
        return adView;
    }
}