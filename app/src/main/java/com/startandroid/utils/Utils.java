package com.startandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import androidx.annotation.Keep;

import com.startandroid.App;

public class Utils {

    public static boolean isNetworkAvailable() {
        ConnectivityManager connection = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connection.getActiveNetworkInfo();
        if (info == null) return false;
        else return info.isConnected();
    }

    public static String fromBase64(String message) {
        byte[] data = Base64.decode(message, Base64.DEFAULT);
        return new String(data);
    }

    @Keep
    public static String xor(String a, boolean b) {
        char[] ax = new char[]{'\u3005', '\u3006'};//々
        try {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < a.length(); i++) {
                output.append((char) (a.charAt(i) ^ ax[i % ax.length]));
            }
            return output.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    static String reverseString(String string) {
        try {
            return new StringBuilder(string).reverse().toString();
        } catch (Exception e) {
            return "";
        }
    }
}