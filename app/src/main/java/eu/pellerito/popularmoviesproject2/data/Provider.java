package eu.pellerito.popularmoviesproject2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class Provider extends ContentProvider {

    // Log_TAG
    private static final String LOG_TAG = "Provider".getClass().getSimpleName();

    private Db db;

    //  int code match UriMatcher

    private static final int CODE_FAVORITES = 100;
    private static final int CODE_FAVORITE = 101;


    // CREATE INSTANCE URI MATCHER
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contract.Favorites.TABLE_NAME,
                CODE_FAVORITES);
        matcher.addURI(authority, Contract.Favorites.TABLE_NAME + "/#",
                CODE_FAVORITE);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        db = new Db(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {

            //TODO TMP TABLE

            case CODE_FAVORITES:

                cursor = db.getReadableDatabase().query(
                        Contract.Favorites.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null, sortOrder
                );

                break;

            case CODE_FAVORITE:

                cursor = db.getReadableDatabase().query(
                        Contract.Favorites.TABLE_NAME,
                        projection,
                        Contract.Favorites.COLUMN_ID + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null, null, sortOrder
                );

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        Log.d(LOG_TAG, "query: " + uri.toString() + ", " + cursor.getCount());

        if(getContext()!= null){
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }


        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
         String type;

        switch (sUriMatcher.match(uri)) {

            case CODE_FAVORITES:
                //noinspection UnusedAssignment
                type = Contract.Favorites.CONTENT_TYPE;
                break;

            case CODE_FAVORITE:
                //noinspection UnusedAssignment
                type = Contract.Favorites.CONTENT_ITEM_TYPE;
                break;
        }

        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        Uri returnUri;
        @SuppressWarnings("UnusedAssignment") long id = -1;


        switch (sUriMatcher.match(uri)) {

            case CODE_FAVORITES:

                id = db.getWritableDatabase().insert(
                        Contract.Favorites.TABLE_NAME,
                        null, values
                );

                break;

            case CODE_FAVORITE:

                id = db.getWritableDatabase().insert(
                        Contract.Favorites.TABLE_NAME,
                        null, values
                );

                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }


        returnUri = ContentUris.withAppendedId(uri, id);

        Log.d(LOG_TAG, "insert: " + uri.toString() + ", " + returnUri.toString());

        if ((!returnUri.getLastPathSegment().equals("-1"))&& (getContext()!= null))
            getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, String where, String[] whereArgs) {
        int rows;

        final SQLiteDatabase myDb = db.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {

            case CODE_FAVORITES:
                rows = myDb.delete(Contract.Favorites.TABLE_NAME, where, whereArgs);
                break;

            case CODE_FAVORITE:
                rows = myDb.delete(Contract.Favorites.TABLE_NAME, Contract.Favorites.
                        COLUMN_ID + " = ?", new String[]{uri.getLastPathSegment()});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if(getContext()!= null) {
            if (rows > 0) getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(LOG_TAG, "delete: " + uri.toString() + ", " + rows);

        return rows;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String where, String[] whereArgs) {

        int rows;

        switch (sUriMatcher.match(uri)) {

            case CODE_FAVORITES:

                rows = db.getWritableDatabase().update(
                        Contract.Favorites.TABLE_NAME,
                        values,
                        where,
                        whereArgs
                );

                break;

            case CODE_FAVORITE:

                rows = db.getWritableDatabase().update(
                        Contract.Favorites.TABLE_NAME,
                        values,
                        Contract.Favorites.COLUMN_ID + " = ?",
                        new String[]{uri.getLastPathSegment()}
                );

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if (rows > 0)
            if(getContext()!= null){
                getContext().getContentResolver().notifyChange(uri, null);
            }

        Log.d(LOG_TAG, "update: " + uri.toString() + ", " + rows);

        return rows;
    }
}
