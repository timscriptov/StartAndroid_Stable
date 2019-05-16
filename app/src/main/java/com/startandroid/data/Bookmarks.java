package com.startandroid.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import static com.startandroid.data.Database.getDatabase;

public final class Bookmarks {
    public static Cursor getAllBookmarks() {
        return getDatabase().rawQuery("SELECT * FROM Bookmarks", null);
    }

    public static boolean add(int num, String title) {
        ContentValues cv = new ContentValues();
        cv.put("Number", num);
        cv.put("Title", title);
        long result = getDatabase().insert("Bookmarks", null, cv);
        return result != -1;
    }

    public static void remove(int num) {
        getDatabase().execSQL("DELETE FROM Bookmarks WHERE Number=" + num);
    }

    public static boolean isBookmarked(int num) {
        @SuppressLint("Recycle") Cursor cursor = getDatabase().rawQuery("SELECT Number FROM Bookmarks WHERE Number = " + num, null);
        return cursor.getCount() != 0;
    }
}
