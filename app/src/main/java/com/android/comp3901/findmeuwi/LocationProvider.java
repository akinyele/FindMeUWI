package com.android.comp3901.findmeuwi;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by Kyzer on 3/25/2017.
 */

public class LocationProvider extends ContentProvider {
    DB_Helper mDB;
    ArrayList<String> locations;

    MatrixCursor matrixCursor;







    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        mDB =DB_Helper.getInstance(getContext());

        String location = null;

        locations = mDB.roomList();

        matrixCursor = new MatrixCursor(
                new String[]{
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
                });

        if(locations != null){

            String query = uri.getLastPathSegment().toString();// Receives query from search bar

            //Finding the limit of the search query
            int limit = Integer.parseInt(uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT));

            int length = locations.size();

            for(int i = 0; i < length && matrixCursor.getCount() < limit; i++){    // Searches through arraylist to see if user query matches
                String place = locations.get(i);


                if(place.toUpperCase().contains(query.toUpperCase())){ // Return a row for each location the query matches
                    matrixCursor.addRow( new Object[] {i, place, i});
                }
            }
        }



        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


   // public Cursor()
}
