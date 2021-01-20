package com.startandroid.utils

import android.app.Activity
import com.startandroid.data.Preferences
import java.util.*

/**
 * @author Тимашков Иван
 * @author https://github.com/TimScriptov
 */
object I18n {
    @JvmStatic
    fun setLanguage(context: Activity) {
        val defaultLocale = context.resources.configuration.locale
        val config = context.resources.configuration
        when (Preferences.getLanguageType()) {
            0 -> config.setLocale(Locale.getDefault())
            1 -> config.setLocale(Locale.ENGLISH)
            2 -> config.setLocale(Locale("ru", "RU"))
            3 -> config.setLocale(Locale("uk")) // Украинский
            4 -> config.setLocale(Locale("th")) // Тайский
            5 -> config.setLocale(Locale.SIMPLIFIED_CHINESE)
            else -> config.setLocale(Locale.getDefault())
        }
        if (defaultLocale != config.locale) context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}