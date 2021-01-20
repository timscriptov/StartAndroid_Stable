package com.startandroid.data

object ScreenMode {
    @JvmStatic
    val currentMode: Mode
        get() = if (Preferences.isInFullscreenMode) Mode.FULLSCREEN else Mode.DEFAULT

    enum class Mode {
        DEFAULT, FULLSCREEN
    }
}