package com.yasmine.mytelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class YelpDatabaseHelper extends SQLiteOpenHelper {
    public YelpDatabaseHelper(@Nullable Context context, @Nullable String name,
                              @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE FAVORITES (ID INTEGER PRIMARY KEY, NAME TEXT, " +
                "CATEGORY TEXT, PRICE REAL, RATING REAL, PHONE_NUMBER INTEGER, LOCATION TEXT, IMAGE TEXT );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
