package com.startandroid.utils;

import com.startandroid.App;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class FileReader {
    @NotNull
    public static String fromAssets(String path) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(App.getContext().getAssets().open(path), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
            return sb.toString();
        } catch (Exception e) {
            return "<p style='color:red;'>Произошла ошибка!</p>";
        }
    }

    @NotNull
    public static String fromUrl(String url) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader((new URL(url).openStream()), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
            return sb.toString();
        } catch (Exception e) {
            return "<p style='color:red;'>Произошла ошибка!</p>";
        }
    }

}
