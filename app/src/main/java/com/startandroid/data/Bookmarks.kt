package com.startandroid.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor

object Bookmarks {
    @JvmStatic
    val allBookmarks: Cursor
        get() = Database.getDatabase().rawQuery("SELECT * FROM Bookmarks", null)

    @JvmStatic
    fun add(num: Int, title: String?): Boolean {
        val cv = ContentValues().apply {
            put("Number", num)
            put("Title", title)
        }
        val result = Database.getDatabase().insert("Bookmarks", null, cv)
        return result != -1L
    }

    @JvmStatic
    fun remove(num: Int) {
        Database.getDatabase().execSQL("DELETE FROM Bookmarks WHERE Number=$num")
    }

    @JvmStatic
    fun isBookmarked(num: Int): Boolean {
        @SuppressLint("Recycle") val cursor = Database.getDatabase()
            .rawQuery("SELECT Number FROM Bookmarks WHERE Number = $num", null)
        return cursor.count != 0
    }
}
