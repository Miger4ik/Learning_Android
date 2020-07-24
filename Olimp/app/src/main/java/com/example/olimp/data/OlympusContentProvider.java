package com.example.olimp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class OlympusContentProvider extends ContentProvider {

    private OlympusDbOpenHelper dbOpenHelper;

    private static final int MEMBERS = 1;
    private static final int MEMBER_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS + "/#", MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new OlympusDbOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS: {
                cursor = db.query(ClubOlympusContract.MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case MEMBER_ID: {
                selection = ClubOlympusContract.MemberEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ClubOlympusContract.MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            default: {
                throw  new IllegalArgumentException("Can`t query incorrect URI " + uri);
            }
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri,  ContentValues values) {
        validateData(values);

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS: {
                long id = db.insert(ClubOlympusContract.MemberEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("insertMethod", "Insertion of data in the table failed for " + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri, null);

                return ContentUris.withAppendedId(uri, id);
            }
            default: {
                throw  new IllegalArgumentException("Insertion of data in the table failed for " + uri);
            }
        }
    }

    @Override
    public int delete(Uri uri,  String selection,  String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        int rowsDeleted;

        switch (match) {
            case MEMBERS: {
                rowsDeleted = db.delete(ClubOlympusContract.MemberEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case MEMBER_ID: {
                selection = ClubOlympusContract.MemberEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(ClubOlympusContract.MemberEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default: {
                throw new IllegalArgumentException("Can`t delete incorrect URI " + uri);
            }
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        validateData(values);

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        int rowsUpdated;

        switch (match) {
            case MEMBERS: {
                rowsUpdated = db.update(ClubOlympusContract.MemberEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case MEMBER_ID: {
                selection = ClubOlympusContract.MemberEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = db.update(ClubOlympusContract.MemberEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default: {
                throw new IllegalArgumentException("Can`t update incorrect URI " + uri);
            }
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return  rowsUpdated;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS: {
                return ClubOlympusContract.MemberEntry.CONTENT_MULTIPLE_ITEMS;
            }
            case MEMBER_ID: {
                return ClubOlympusContract.MemberEntry.CONTENT_SINGLE_ITEM;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
    }

    private void validateData(ContentValues values) {
        if (values.containsKey(ClubOlympusContract.MemberEntry.COLUMN_FIRST_NAME)) {
            String firstName = values.getAsString(ClubOlympusContract.MemberEntry.COLUMN_FIRST_NAME);
            if (firstName == null) {
                throw new IllegalArgumentException("You have to input first name");
            }
        }

        if (values.containsKey(ClubOlympusContract.MemberEntry.COLUMN_LAST_NAME)) {
            String lastName = values.getAsString(ClubOlympusContract.MemberEntry.COLUMN_LAST_NAME);
            if (lastName == null) {
                throw new IllegalArgumentException("You have to input last name");
            }
        }

        if (values.containsKey(ClubOlympusContract.MemberEntry.COLUMN_GENDER)) {
            Integer gender = values.getAsInteger(ClubOlympusContract.MemberEntry.COLUMN_GENDER);
            if (gender == null || !(gender == ClubOlympusContract.MemberEntry.GENDER_UNKNOWN || gender == ClubOlympusContract.MemberEntry.GENDER_MALE || gender == ClubOlympusContract.MemberEntry.GENDER_FEMALE)) {
                throw new IllegalArgumentException("You have to input gender");
            }
        }

        if (values.containsKey(ClubOlympusContract.MemberEntry.COLUMN_SPORT)) {
            String sport = values.getAsString(ClubOlympusContract.MemberEntry.COLUMN_SPORT);
            if (sport == null) {
                throw new IllegalArgumentException("You have to input sport");
            }
        }
    }
}
