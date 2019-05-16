package com.startandroid.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.startandroid.App;

public final class Preferences {
    private static SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());

    public static boolean isInGridMode() {
        return preferences.getBoolean("grid_mode", false);
    }

    public static boolean isInNightMode() {
        return preferences.getBoolean("night_mode", false);
    }

    public static void setNightMode(boolean value) {
        preferences.edit().putBoolean("night_mode", value).apply();
    }

    public static boolean isInFullscreenMode() {
        return preferences.getBoolean("fullscreen_mode", false);
    }

    public static void setFullscreenMode(boolean value) {
        preferences.edit().putBoolean("fullscreen_mode", value).apply();
    }

    public static void setRated() {
        preferences.edit().putBoolean("isRated", true).apply();
    }

    public static boolean isRated() {
        return preferences.getBoolean("isRated", false);
    }

    public static String getBookmark() {
        return preferences.getString("bookmark", null);
    }

    public static void setBookmark(String url) {
        preferences.edit().putString("bookmark", url).apply();
    }

    public static String getLang() {
        return preferences.getString("lang", "ru");
    }

    public static void setLang(String lang) {
        preferences.edit().putString("lang", lang).apply();
    }

    public static String getFontType() {
        return preferences.getString("font", "fonts/DroidSans.ttf");
    }

    public static String getFontSize() {
        return preferences.getString("font_size", "100%");
    }

    public static boolean isFirstLaunch() {
        return preferences.getBoolean("firstLaunch", true);
    }

    public static void setFirstLaunch(boolean value) {
        preferences.edit().putBoolean("firstLaunch", value).apply();
    }

    public static boolean isOffline() {
        return preferences.getBoolean("offline", false);
    }
}
