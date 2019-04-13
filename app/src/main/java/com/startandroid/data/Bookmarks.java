package com.startandroid.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

public final class Bookmarks {
    public static Cursor getAllBookmarks() {
        return Database.getDatabase().rawQuery("SELECT * FROM Bookmarks", null);
    }

    public static boolean add(int num, String title) {
        ContentValues cv = new ContentValues();
        cv.put("Number", num);
        cv.put("Title", title);
        long result = Database.getDatabase().insert("Bookmarks", null, cv);
        return result != -1;
    }

    public static void remove(int num) {
        Database.getDatabase().execSQL("DELETE FROM Bookmarks WHERE Number=" + num);
    }

    public static boolean isBookmarked(int num) {
        @SuppressLint("Recycle") Cursor cursor = Database.getDatabase().rawQuery("SELECT Number FROM Bookmarks WHERE Number = " + num, null);
        return cursor.getCount() != 0;
    }

}
