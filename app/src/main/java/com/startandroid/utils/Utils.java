package com.startandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.startandroid.App;
import org.jetbrains.annotations.NotNull;

public class Utils {

    public static boolean isNetworkAvailable() {
        ConnectivityManager connection = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connection.getActiveNetworkInfo();
        if (info == null) return false;
        else return info.isConnected();
    }

    @NotNull
    static String reverseString(String string) {
        try {
            return new StringBuilder(string).reverse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}