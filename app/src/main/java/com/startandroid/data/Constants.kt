package com.startandroid.data

object Constants {
    @JvmStatic
    val resPath: String
        get() = if (Preferences.offline) {
            "data/data/com.startandroid/files/resources/pages"
        } else {
            "https://timscriptov.github.io/StartAndroid/pages"
        }
}
