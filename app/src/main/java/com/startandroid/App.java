package com.startandroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.startandroid.module.Ads;

import es.dmoral.toasty.Toasty;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Ads.initialize(this);
    }

    public static void toast(String msg){
        Toasty.info(context, msg).show();
    }

    public static void toast(int res){
        Toasty.info(context, context.getResources().getString(res)).show();
    }
}
