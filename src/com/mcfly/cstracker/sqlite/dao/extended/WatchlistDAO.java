package com.mcfly.cstracker.sqlite.dao.extended;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.mcfly.cstracker.sqlite.contentprovider.CollectionContentProvider;
import com.mcfly.cstracker.sqlite.contentprovider.WatchlistContentProvider;
import com.mcfly.cstracker.sqlite.dao.BaseDAO;
import com.mcfly.cstracker.sqlite.dal.Bought_Comics;
import com.mcfly.cstracker.sqlite.dal.Watchlist;
import com.mcfly.cstracker.sqlite.dal.wrapper.Bought_ComicsDalWrapper;
import com.mcfly.cstracker.sqlite.dal.wrapper.WatchlistDalWrapper;
import com.mcfly.cstracker.sqlite.dao.extended.interfaces.IWatchlist;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;

public class WatchlistDAO extends BaseDAO implements IWatchlist {

    private final static String TAG = WatchlistDAO.class.getName();

    public WatchlistDAO(Context c) {
        super(c, WatchlistContentProvider.CONTENT_URI);
    }
    
    @Override
    public int save(Watchlist object) throws BaseDAOException {
    	Uri uri = context.getContentResolver()
    		.insert(contentProviderUri, WatchlistDalWrapper.getContentValueFromObject(object));
		if(uri==null) {
			throw new BaseDAOException("Error while saving to watchlist");
		}
		String id = uri.toString();
		return Integer.valueOf(id);
    }
    
    public Cursor getLibraryCursor() {
		Cursor cursor = context.getContentResolver().query(
				WatchlistContentProvider.CONTENT_ITEMS,
				null,
				null, 
				null, 
				null);
		return cursor;
	}

}
