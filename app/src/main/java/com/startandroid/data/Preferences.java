package com.startandroid.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.startandroid.App;

public final class Preferences {
    private static SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getContext());

    public static boolean isInGridMode() {
        return preferences.getBoolean(Constants.GRID_MODE, false);
    }

    public static boolean isInNightMode() {
        return preferences.getBoolean(Constants.NIGHT_MODE, false);
    }

    public static void setNightMode(boolean value) {
        preferences.edit().putBoolean(Constants.NIGHT_MODE, value).apply();
    }

    public static boolean isInFullscreenMode() {
        return preferences.getBoolean(Constants.FULLSCREEN_MODE, false);
    }

    public static void setFullscreenMode(boolean value) {
        preferences.edit().putBoolean(Constants.FULLSCREEN_MODE, value).apply();
    }

    public static void setRated() {
        preferences.edit().putBoolean(Constants.IS_RATED, true).apply();
    }

    public static boolean isRated() {
        return preferences.getBoolean(Constants.IS_RATED, false);
    }

    public static String getBookmark() {
        return preferences.getString(Constants.BOOKMARK, null);
    }

    public static void setBookmark(String url) {
        preferences.edit().putString(Constants.BOOKMARK, url).apply();
    }

    public static String getLang() {
        return preferences.getString(Constants.LANG, "ru");
    }

    public static void setLang(String lang) {
        preferences.edit().putString(Constants.LANG, lang).apply();
    }

    public static String getFontType() {
        return preferences.getString(Constants.FONT, "fonts/DroidSans.ttf");
    }

    public static String getFontSize() {
        return preferences.getString(Constants.FONT_SIZE, "100%");
    }

    public static boolean isFirstLaunch() {
        return preferences.getBoolean(Constants.FIRSTLAUNCH, true);
    }

    public static void setFirstLaunch(boolean value) {
        preferences.edit().putBoolean(Constants.FIRSTLAUNCH, value).apply();
    }

    public static boolean isOffline() {
        return preferences.getBoolean(Constants.OFFLINE, false);
    }

    public static void setOffline(boolean value) {
        preferences.edit().putBoolean(Constants.OFFLINE, value).apply();
    }
}
