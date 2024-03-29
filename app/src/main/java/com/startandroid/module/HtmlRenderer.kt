package com.startandroid.module

import com.startandroid.data.Preferences
import com.startandroid.utils.FileReader
import org.jetbrains.annotations.Contract

object HtmlRenderer {
    @JvmStatic
    fun renderHtml(html: String): String {
        return html
            .replace("<head>", "<head>$style")
            .replace(
                "androidstudio.css",
                if (Preferences.nightMode) "darkcode.css" else "androidstudio.css"
            )
            .replace("<body>", "<body>$translatePlugin")
            .replace("<body>", if (Preferences.nightMode) "<body style='$darkMode'>" else "<body>")
    }

    private val style: String
        get() = ("<style>@font-face{font-family:CustomFont; src:url(file:///android_asset/" + Preferences.fontType + ");}"
                + "p, h1, h2, h3, table, ul, ol {font-size:" + Preferences.fontSize + "; font-family:CustomFont;}"
                + "pre,code {font-size:" + Preferences.fontSize + "; font-family:CustomFont;}"
                + ".goog-te-banner-frame{display:none;}"
                + if (Preferences.nightMode) "$darkMode</style>" else "</style>")

    @get:Contract(pure = true)
    private val darkMode: String
        get() = "background:#323232; color:#FAFAFA;"

    private val translatePlugin: String
        get() = FileReader.fromAssets("tt.html")
}