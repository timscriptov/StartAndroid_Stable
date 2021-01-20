package com.startandroid.data

import android.preference.PreferenceManager
import com.startandroid.App
import com.startandroid.App.Companion.getContext

object Preferences {
    @JvmStatic
    var webViewCore: Int
        get() = App.getPreferences().getInt("first_loaded", 0)
        set(i) {
            App.getPreferences().edit().putInt("first_loaded", i).apply()
        }

    @JvmStatic
    var languageType: Int
        get() = App.getPreferences().getInt("first_loaded", 0)
        set(i) {
            App.getPreferences().edit().putInt("first_loaded", i).apply()
        }

    @JvmStatic
    val isInGridMode: Boolean
        get() = App.getPreferences().getBoolean("grid_mode", false)

    @JvmStatic
    val isInNightMode: Boolean
        get() = App.getPreferences().getBoolean("night_mode", false)

    @JvmStatic
    fun setNightMode(value: Boolean) {
        App.getPreferences().edit().putBoolean("night_mode", value).apply()
    }

    @JvmStatic
    val isInFullscreenMode: Boolean
        get() = App.getPreferences().getBoolean("fullscreen_mode", false)

    @JvmStatic
    fun setFullscreenMode(value: Boolean) {
        App.getPreferences().edit().putBoolean("fullscreen_mode", value).apply()
    }

    @JvmStatic
    fun setRated() {
        App.getPreferences().edit().putBoolean("isRated", true).apply()
    }

    @JvmStatic
    val isRated: Boolean
        get() = App.getPreferences().getBoolean("isRated", false)

    @JvmStatic
    var bookmark: String?
        get() = App.getPreferences().getString("bookmark", null)
        set(url) {
            App.getPreferences().edit().putString("bookmark", url).apply()
        }

    @JvmStatic
    var lang: String?
        get() = App.getPreferences().getString("lang", "ru")
        set(lang) {
            App.getPreferences().edit().putString("lang", lang).apply()
        }

    @JvmStatic
    val fontType: String?
        get() = App.getPreferences().getString("font", "fonts/DroidSans.ttf")

    @JvmStatic
    val fontSize: String?
        get() = App.getPreferences().getString("font_size", "100%")

    @JvmStatic
    var isFirstLaunch: Boolean
        get() = App.getPreferences().getBoolean("firstLaunch", true)
        set(value) {
            App.getPreferences().edit().putBoolean("firstLaunch", value).apply()
        }

    @JvmStatic
    var offline: Boolean
        get() = App.getPreferences().getBoolean("offline", false)
        set(value) {
            App.getPreferences().edit().putBoolean("offline", value).apply()
        }
}