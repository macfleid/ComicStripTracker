package com.mcfly.cstracker.sqlite.contentprovider;
 
 
import com.mcfly.cstracker.sqlite.DbManager;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import com.mcfly.cstracker.sqlite.dal.Collection;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class CollectionContentProvider extends ContentProvider {
    private DbManager dbManager;
    private static final String BASE_PATH = "Collection";
    private static final String WATCHLIST_PATH = "Collection_watchlist";
    private static final String LIBRARY_PATH = "Collection_library";
    
    private static final String VIEW_LIBRARY = "VIEW_COLLECTION_LIBRARY";
    private static final String VIEW_WATCHLIST = "VIEW_COLLECTION_WATCHLIST";

    public static final String AUTHORITY = "com.mcfly.cstracker.sqlite.contentprovider.CollectionContentProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" +AUTHORITY+ "/" + BASE_PATH);
    public static final Uri CONTENT_LIBRARY_URI = Uri.parse("content://" +AUTHORITY+ "/" + BASE_PATH + "/" + LIBRARY_PATH );
    public static final Uri CONTENT_WATCHLIST_URI = Uri.parse("content://" +AUTHORITY+ "/" + BASE_PATH + "/" + WATCHLIST_PATH);
    public static final String TYPE = "Collection";

    private static final int DEFAULT_CODE = 1;
    private static final int WATCHLIST_CODE = 2;
    private static final int LIBRARY_CODE = 3;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        static {
            sURIMatcher.addURI(AUTHORITY, BASE_PATH, DEFAULT_CODE);
            sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/" + WATCHLIST_PATH, WATCHLIST_CODE);
            sURIMatcher.addURI(AUTHORITY, BASE_PATH+ "/" + LIBRARY_PATH, LIBRARY_CODE);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = dbManager.getWritableDatabase();
        int nb = database.delete(Collection.TABLE_NAME, selection, null);
        if(nb>0) {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return nb;
    }
 
    @Override
    public String getType(Uri uri) {
        return CollectionContentProvider.TYPE;
    }
 
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = dbManager.getWritableDatabase();
        long id = database.insert(Collection.TABLE_NAME, null, values);
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
        	queryBuilder.setTables(Collection.TABLE_NAME);
        	break;
        case WATCHLIST_CODE:
        	queryBuilder.setTables(VIEW_WATCHLIST);
        	break;
        case LIBRARY_CODE:
        	queryBuilder.setTables(VIEW_LIBRARY);
        	break;
        }
        
        Cursor cursor = queryBuilder.query(database,null,selection,null,groupBy,null,null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
 
    @Override
    public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
        SQLiteDatabase database = dbManager.getWritableDatabase();
        int nbColumn = database.update(Collection.TABLE_NAME, values, selection, selectionArgs);
        if(nbColumn > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return nbColumn;
    }
}