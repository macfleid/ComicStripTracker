package com.mcfly.cstracker.sqlite.dao.extended;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.mcfly.cstracker.sqlite.contentprovider.CollectionContentProvider;
import com.mcfly.cstracker.sqlite.cursor.CollectionCursor;
import com.mcfly.cstracker.sqlite.dao.BaseDAO;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.dal.Watchlist;
import com.mcfly.cstracker.sqlite.dal.wrapper.CollectionDalWrapper;
import com.mcfly.cstracker.sqlite.dal.wrapper.WatchlistDalWrapper;
import com.mcfly.cstracker.sqlite.dao.extended.interfaces.ICollection;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;

public class CollectionDAO extends BaseDAO implements ICollection {

    private final static String TAG = CollectionDAO.class.getName();

    public CollectionDAO(Context c) {
        super(c, CollectionContentProvider.CONTENT_URI);
    }

	@Override
	public Collection getCollection(String name) {
		Cursor cursor = context.getContentResolver().query(contentProviderUri, 
				null, 
				String.format("%s='%s'", Collection.COLUMN_NAME, name), 
				null, 
				null);
		Collection result = null;
		if(cursor.moveToFirst()) {
			result = CollectionDalWrapper.getObjectFromDB(new CollectionCursor(cursor), 0);
		}
		cursor.close();
		return result;
	}
    
	
	@Override
	public int save(Collection object) throws BaseDAOException {
		Uri uri = context.getContentResolver()
				.insert(contentProviderUri, CollectionDalWrapper.getContentValueFromObject(object));
		if(uri==null) {
			throw new BaseDAOException("Error while saving to watchlist");
		}
		String id = uri.toString();
		return Integer.valueOf(id);
	}
	
	public Cursor getLibraryCursor() {
		Cursor cursor = context.getContentResolver().query(
				CollectionContentProvider.CONTENT_LIBRARY_URI,
				null,
				null, 
				null, 
				null);
		return cursor;
	}


	public Cursor getWatchListCursor() {
		Cursor cursor = context.getContentResolver().query(
				CollectionContentProvider.CONTENT_LIBRARY_URI,
				null,
				null, 
				null, 
				null);
		return cursor;
	}
}
