package com.startandroid.module;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.startandroid.BuildConfig;
import com.startandroid.data.Constants;

public class Ads {
    private static InterstitialAd interstitialAd;
    private final String ADMOB_BANNER_ID = BuildConfig.DEBUG ? "ca-app-pub-3940256099942544/6300978111" : Constants.ADMOB_BANNER;//ca-app-pub-1411495427741055/5959092554
    private final String ADMOB_INTERSTITIAL_ID = BuildConfig.DEBUG ? "ca-app-pub-3940256099942544/1033173712" : Constants.ADMOB_INTERSTITIAL;//ca-app-pub-1411495427741055/3241004842
    private long lastShownTime = 0;
    private AdView adView;

    public static void initialize(Context context) {
        MobileAds.initialize(context, Constants.INITIALIZE);//ca-app-pub-1411495427741055~3216954118
    }

    public AdView getBanner(Context context) {
        adView = new AdView(context);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(ADMOB_BANNER_ID);
        adView.loadAd(new AdRequest.Builder().build());
        return adView;
    }

    public void loadInterstitial(Context context) {
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        interstitialAd.setAdUnitId(ADMOB_INTERSTITIAL_ID);
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void showInsAd() {
        if (interstitialAd == null) return;

        if (interstitialAd.isLoaded() && lastShownTime + 20000 < System.currentTimeMillis()) {
            interstitialAd.show();
            lastShownTime = System.currentTimeMillis();
        }
    }
}