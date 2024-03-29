package com.startandroid.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.startandroid.App;
import org.jetbrains.annotations.NotNull;

public final class Database extends SQLiteOpenHelper {
    private static SQLiteDatabase database;

    public Database() {
        super(App.getContext(), "startandroid", null, 1);
        database = getWritableDatabase();
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        // Номер урока
        db.execSQL("CREATE TABLE ReadLessons (Number INTEGER PRIMARY KEY)");
        // Название урока
        db.execSQL("CREATE TABLE Bookmarks (Number INTEGER PRIMARY KEY, Title TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("onUpgrade", ": " + db + ": " + oldVersion + ": " + newVersion);
    }
}
