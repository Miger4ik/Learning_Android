package com.example.olimp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OlympusDbOpenHelper extends SQLiteOpenHelper {

    public OlympusDbOpenHelper(Context context) {
        super(context, ClubOlympusContract.DATABASE_NAME, null, ClubOlympusContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + ClubOlympusContract.MemberEntry.TABLE_NAME
                + "(" + ClubOlympusContract.MemberEntry._ID + " INTEGER PRIMARY KEY, "
                + ClubOlympusContract.MemberEntry.COLUMN_FIRST_NAME + " TEXT,"
                + ClubOlympusContract.MemberEntry.COLUMN_LAST_NAME + " TEXT,"
                + ClubOlympusContract.MemberEntry.COLUMN_GENDER + " INTEGER NOT NULL,"
                + ClubOlympusContract.MemberEntry.COLUMN_SPORT + " TEXT" + ")";
        db.execSQL(CREATE_MEMBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ClubOlympusContract.DATABASE_NAME);
        onCreate(db);
    }
}
