package com.startandroid.module;

import com.startandroid.data.Preferences;
import com.startandroid.utils.FileReader;

public class HtmlRenderer {
    public static String renderHtml(String html) {
        return html
                .replace("<head>", "<head>" + getStyle())
                .replace("androidstudio.css", (Preferences.isInNightMode() ? "darkcode.css" : "androidstudio.css"))
                .replace("<body>", "<body>" + getTranslatePlugin())
                .replace("<body>", (Preferences.isInNightMode() ? "<body style='" + getDarkMode() + "'>" : "<body>"));
    }

    private static String getStyle() {
        return "<style>@font-face{font-family:CustomFont; src:url(file:///android_asset/" + Preferences.getFontType() + ");}"
                + "p, h1, h2, h3, table, ul, ol {font-size:" + Preferences.getFontSize() + "; font-family:CustomFont;}"
                + "pre,code {font-size:" + Preferences.getFontSize() + "; font-family:CustomFont;}"
                + ".goog-te-banner-frame{display:none;}"
                + (Preferences.isInNightMode() ? getDarkMode() + "</style>" : "</style>");
    }

    private static String getDarkMode() {
        return "background:#323232; color:#FAFAFA;";
    }

    private static String getTranslatePlugin() {
        return FileReader.fromAssets("tt.html");
    }
}
