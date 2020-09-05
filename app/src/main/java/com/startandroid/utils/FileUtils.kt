package com.startandroid.utils

import android.content.Context
import java.io.File

object FileUtils {
    fun deleteOffline(context: Context) {
        val archive = File(context.filesDir.toString() + "/offline.zip")
        val resourcesDir = File(context.filesDir.toString() + "/resources")
        try {
            if (archive.exists()) {
                archive.delete()
            }
            if (resourcesDir.exists()) {
                deleteDirectory(resourcesDir)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun deleteDirectory(directory: File): Boolean {
        if (directory.exists()) {
            val files = directory.listFiles()
            if (null != files) {
                for (i in files.indices) {
                    if (files[i].isDirectory) {
                        deleteDirectory(files[i])
                    } else {
                        files[i].delete()
                    }
                }
            }
        }
        return directory.delete()
    }
}