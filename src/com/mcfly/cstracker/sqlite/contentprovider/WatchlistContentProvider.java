package com.mcfly.cstracker.sqlite.contentprovider;
 
 
import com.mcfly.cstracker.sqlite.DbManager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import com.mcfly.cstracker.sqlite.dal.Watchlist;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class WatchlistContentProvider extends ContentProvider {
    private DbManager dbManager;
    private static final String BASE_PATH = "Watchlist";
    private static final String ITEMS_PATH = "ITEMS_PATH";

    public static final String AUTHORITY = "com.mcfly.cstracker.sqlite.contentprovider.WatchlistContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" +AUTHORITY+ "/" + BASE_PATH);
    public static final Uri CONTENT_ITEMS = Uri.parse("content://" +AUTHORITY+ "/" + BASE_PATH + "/" + ITEMS_PATH);
    public static final String TYPE = "Watchlist";

    private static final int DEFAULT_CODE = 1;
    private static final int ITEMS_CODE = 2;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        static {
            sURIMatcher.addURI(AUTHORITY, BASE_PATH, DEFAULT_CODE);
            sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/" + ITEMS_PATH, ITEMS_CODE);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbManager.getWritableDatabase();
        int nb = database.delete(Watchlist.TABLE_NAME, selection, null);
        if(nb>0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return nb;
    }
 
    @Override
    public String getType(Uri uri) {
        return WatchlistContentProvider.TYPE;
    }
 
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = dbManager.getWritableDatabase();
        long id = database.insert(Watchlist.TABLE_NAME, null, values);
        if(id>0) {
            getContext().getContentResolver().notifyChange(uri,null);
            return Uri.parse(String.valueOf(id));
        }
        return null;
    }
 
    @Override
    public boolean onCreate() {
        dbManager =  DbManager.getInstance(getContext());
        return false;
    }
 
    @Override 
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) { 
        SQLiteDatabase database =  dbManager.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String groupBy = "";

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
        case DEFAULT_CODE:
        	queryBuilder.setTables(Watchlist.TABLE_NAME);
        	break;
        case ITEMS_CODE:
        	queryBuilder.setTables("VIEW_WATCHLIST_ITEMS");
        	break;
        }
        Cursor cursor = queryBuilder.query(database,null,selection,null,groupBy,null,null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
 
    @Override
    public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
        SQLiteDatabase database = dbManager.getWritableDatabase();
        int nbColumn = database.update(Watchlist.TABLE_NAME, values, selection, selectionArgs);
        if(nbColumn > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return nbColumn;
    }
}