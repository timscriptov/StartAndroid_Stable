package com.startandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.startandroid.data.Database
import com.startandroid.data.Preferences
import es.dmoral.toasty.Toasty
import org.jetbrains.annotations.Nullable

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        Database()
        if (Preferences.isInNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        private var app: Application? = null
        private var preferences: SharedPreferences? = null

        @JvmStatic
        fun getContext(): Context? {
            if (context == null) {
                context = App()
            }
            return context
        }

        fun getApp(): Application? {
            if (app == null) {
                app = App()
            }
            return app
        }

        @JvmStatic
        @Nullable
        fun getPreferences(): SharedPreferences {
            if (preferences == null) {
                preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext())
            }
            return preferences!!
        }

        @JvmStatic
        fun toast(msg: String?) {
            Toasty.info(context!!, msg!!).show()
        }

        @JvmStatic
        fun toast(res: Int) {
            Toasty.info(context!!, context!!.resources.getString(res)).show()
        }
    }
}