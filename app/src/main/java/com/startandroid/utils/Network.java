package com.startandroid.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.startandroid.App;

public class Network {
    public static boolean isNetworkAvailable(){
        ConnectivityManager connection = (ConnectivityManager) App.getContext().getSystemService(App.getContext().CONNECTIVITY_SERVICE);
        NetworkInfo info = connection.getActiveNetworkInfo();
        if(info!=null){
            return false;
        }
        return false;
    }
}
