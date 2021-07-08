package com.mcal.mcpelauncher.utils

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.startandroid.data.Constants

object AdsAdmob : AdListener() {
    private var interstitialAd: InterstitialAd? = null

    @JvmStatic
    fun loadInterestialAd(context: Context) {
        InterstitialAd.load(context, Constants.intertialId, AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(p0: InterstitialAd) {
                    super.onAdLoaded(p0)
                    interstitialAd = p0
                }
            })
    }

    @JvmStatic
    fun showInterestialAd(activity: Activity, callback: (() -> Unit)? = null) {
        if (interstitialAd != null) {
            interstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    interstitialAd = null
                    callback?.invoke()
                }
            }
            interstitialAd!!.show(activity)
        } else {
            callback?.invoke()
        }
    }

    @JvmStatic
    fun showBannerAd(context: Context): AdView {
        val adView = AdView(context)
        adView.adSize = AdSize.SMART_BANNER
        adView.adUnitId = Constants.bannerId
        adView.adListener = AdsAdmob
        adView.loadAd(AdRequest.Builder().build())
        return adView
    }
}