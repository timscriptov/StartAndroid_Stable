package com.startandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.startandroid.data.Database
import com.startandroid.data.Preferences
import org.jetbrains.annotations.Nullable

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        Database()
        if (Preferences.nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        private lateinit var preferences: SharedPreferences

        @JvmStatic
        fun getContext(): Context {
            return context
        }

        @JvmStatic
        @Nullable
        fun getPreferences(): SharedPreferences {
            return preferences
        }
    }
}