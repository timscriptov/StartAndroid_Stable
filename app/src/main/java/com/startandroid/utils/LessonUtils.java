package com.startandroid.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.startandroid.App;
import com.startandroid.data.Database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.startandroid.data.Constants.PACKAGE_NAME;
import static com.startandroid.data.Preferences.isOffline;

public class LessonUtils {
    public static boolean isRead(int num) {
        @SuppressLint("Recycle") Cursor cursor = Database.getDatabase().rawQuery("SELECT Number FROM ReadLessons WHERE Number = " + num, null);
        return cursor.getCount() != 0;
    }

    public static boolean markAsRead(int num) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Number", num);
        return Database.getDatabase().insert("ReadLessons", null, contentValues) != -1;
    }

    public static int getLessonNumberByTitle(String title) {
        Pattern p = Pattern.compile("\\w+ (\\d+).*");
        Matcher m = p.matcher(title);
        if (m.matches()) return Integer.valueOf(m.group(1));
        else return 1;
    }

    public static int getLessonNumberByUrl(String url) {
        Pattern p = Pattern.compile(".*/lesson_(\\d+).html.*");
        Matcher m = p.matcher(url);
        if (m.matches()) return Integer.parseInt(m.group(1));
        return 24;
    }

}
