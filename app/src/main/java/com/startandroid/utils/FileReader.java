package com.startandroid.utils;

import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.startandroid.App;
import com.startandroid.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.SSLException;

public class FileReader {
    public static String fromAssets(String path) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(App.getContext().getAssets().open(path), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
            return sb.toString();
        } catch (Exception e) {
            return "<font color=\"color:red;\">" + App.getContext().getString(R.string.error) + ": </font>" + Log.getStackTraceString(e);
        }
    }

    public static String fromUrl(String url) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader((new URL(url).openStream()), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
            return sb.toString();
        } catch (Exception e) {
            if (e instanceof SSLException) {
                try {
                    ProviderInstaller.installIfNeeded(App.getContext());
                    return fromUrl(url);
                } catch (GooglePlayServicesRepairableException e1) {
                    return "<font color=\"color:red;\">" + App.getContext().getString(R.string.error) + ": </font>" + Log.getStackTraceString(e1);
                } catch (GooglePlayServicesNotAvailableException e1) {
                    return "<font color=\"color:red;\">" + App.getContext().getString(R.string.google_play_services_not_available) + ": </font>";
                }
            }
            return "<font color=\"color:red;\">" + App.getContext().getString(R.string.error) + ": </font>" + Log.getStackTraceString(e);
        }
    }

    public static String fromStorage(String path) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
            return sb.toString();
        } catch (Exception exception) {
            return "<font color=\"color:red;\">" + App.getContext().getString(R.string.error) + ": </font>" + Log.getStackTraceString(exception);
        }
    }
}
