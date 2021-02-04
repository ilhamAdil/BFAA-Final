package com.dicoding.listviewparcel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbuser";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_USER = String.format("CREATE TABLE %s"
            + " (%s TEXT PRIMARY KEY NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.FavColumns.USERNAME,
            DatabaseContract.FavColumns.AVATAR,
            DatabaseContract.FavColumns.FOLLOWERS,
            DatabaseContract.FavColumns.FOLLOWING,
            DatabaseContract.FavColumns.REPO
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
