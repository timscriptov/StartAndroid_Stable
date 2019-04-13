package com.startandroid.data;

import android.support.v7.app.AppCompatDelegate;

public final class NightMode {

    public static Mode getCurrentMode() {
        return Preferences.isInNightMode() ? Mode.NIGHT : Mode.DAY;
    }

    public static void setMode(Mode mode) {
        if (mode.equals(Mode.NIGHT)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public enum Mode {
        NIGHT, DAY
    }
}
