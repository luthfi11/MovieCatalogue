package com.luthfialfarisi.moviecatalogue.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.luthfialfarisi.moviecatalogue.utils.DatabaseContract.MovieColumns.*;
import static com.luthfialfarisi.moviecatalogue.utils.DatabaseContract.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "db_movie";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_FAVORITE = "create table "+ TABLE_FAVORITE + " (" +
            _ID +" integer primary key autoincrement, " +
            POSTER +" text not null, " +
            BACKDROP +" text not null, " +
            TITLE +" text not null, " +
            RELEASE_DATE +" text not null, " +
            POPULARITY +" text not null, " +
            OVERVIEW +" text not null );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVORITE);
        onCreate(db);
    }
}
