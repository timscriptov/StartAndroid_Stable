package com.startandroid.utils;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileUtils {
    public static void deleteOffline(@NotNull Context context) {
        File archive = new File(context.getFilesDir() + "/offline.zip");
        File resourcesDir = new File(context.getFilesDir() + "/resources");
        try {
            if (archive.exists()) {
                archive.delete();
            }
            if (resourcesDir.exists()) {
                deleteDirectory(resourcesDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDirectory(@NotNull File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
        }
        return (directory.delete());
    }
}
