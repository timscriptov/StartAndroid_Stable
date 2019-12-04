package com.startandroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import com.startandroid.data.Database;
import com.startandroid.module.Ads;
import com.startandroid.utils.LocaleHelper;

import es.dmoral.toasty.Toasty;

public class App extends Application {

    public static SharedPreferences preferences;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void toast(String msg) {
        Toasty.info(context, msg).show();
    }

    public static void toast(int res) {
        Toasty.info(context, context.getResources().getString(res)).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Ads.initialize(this);
        new Database();
    }

    @Override
    protected void attachBaseContext(Context base) {
        // Иначе не будут ресолвиться стринги из ресурсов, и половина аппы будет на языке системы,
        // а другая половина на языке, который выбран в настройках
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Иначе не будут ресолвиться стринги из ресурсов, и половина аппы будет на языке системы,
        // а другая половина на языке, который выбран в настройках
        LocaleHelper.onAttach(this);
    }
}
