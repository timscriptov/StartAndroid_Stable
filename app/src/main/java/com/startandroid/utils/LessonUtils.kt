package com.startandroid.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import com.startandroid.data.Database
import java.util.regex.Pattern

object LessonUtils {
    @JvmStatic
    fun isRead(num: Int): Boolean {
        @SuppressLint("Recycle") val cursor = Database.getDatabase()
            .rawQuery("SELECT Number FROM ReadLessons WHERE Number = $num", null)
        return cursor.count != 0
    }

    @JvmStatic
    fun markAsRead(num: Int): Boolean {
        val contentValues = ContentValues()
        contentValues.put("Number", num)
        return Database.getDatabase().insert("ReadLessons", null, contentValues) != -1L
    }

    @JvmStatic
    fun getLessonNumberByTitle(title: String?): Int {
        val p = Pattern.compile("\\w+ (\\d+).*")
        val m = p.matcher(title)
        return if (m.matches()) m.group(1).toInt() else 1
    }

    @JvmStatic
    fun getLessonNumberByUrl(url: String?): Int {
        val p = Pattern.compile(".*/lesson_(\\d+).html.*")
        val m = p.matcher(url)
        return if (m.matches()) m.group(1).toInt() else 1
    }
}