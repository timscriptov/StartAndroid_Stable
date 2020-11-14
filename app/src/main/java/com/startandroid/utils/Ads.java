package com.startandroid.utils;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.startandroid.App;
import com.startandroid.activities.LessonActivity;
import com.startandroid.data.Constants;

import org.jetbrains.annotations.NotNull;

public class Ads extends AdListener {

    public static InterstitialAd insAd;

    public static @NotNull AdView getBanner() {
        AdView adView = new AdView(App.getContext());
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(Constants.bannerId);
        adView.setAdListener(new Ads());
        adView.loadAd(new AdRequest.Builder().build());
        return adView;
    }

    public static void loadInsAd() {
        if (insAd != null && !insAd.isLoading()) {
            insAd.loadAd(new AdRequest.Builder().build());
            return;
        }
        insAd = new InterstitialAd(App.getContext());
        insAd.setAdListener(new InsAdListener());
        insAd.setAdUnitId(Constants.intertialId);
        insAd.loadAd(new AdRequest.Builder().build());
    }

    public static void showInsAd() {
        if (insAd.isLoaded()) {
            insAd.show();
        }
    }

    @Override
    public void onAdFailedToLoad(int p1) {
        super.onAdFailedToLoad(p1);
        LessonActivity.adLayout.removeAllViews();
    }
}