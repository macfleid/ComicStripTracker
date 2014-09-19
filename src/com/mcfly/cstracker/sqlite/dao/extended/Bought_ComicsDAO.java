package com.mcfly.cstracker.sqlite.dao.extended;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.mcfly.cstracker.sqlite.contentprovider.Bought_ComicsContentProvider;
import com.mcfly.cstracker.sqlite.dao.BaseDAO;
import com.mcfly.cstracker.sqlite.dal.BD;
import com.mcfly.cstracker.sqlite.dal.Bought_Comics;
import com.mcfly.cstracker.sqlite.dal.wrapper.BDDalWrapper;
import com.mcfly.cstracker.sqlite.dal.wrapper.Bought_ComicsDalWrapper;
import com.mcfly.cstracker.sqlite.dao.extended.interfaces.IBought_Comics;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;

public class Bought_ComicsDAO extends BaseDAO implements IBought_Comics {

    private final static String TAG = Bought_ComicsDAO.class.getName();

    public Bought_ComicsDAO(Context c) {
        super(c, Bought_ComicsContentProvider.CONTENT_URI);
    }
    
    @Override
    public int save(Bought_Comics object) throws BaseDAOException {
    	Uri uri = context.getContentResolver()
    		.insert(contentProviderUri, Bought_ComicsDalWrapper.getContentValueFromObject(object));
		if(uri==null) {
			throw new BaseDAOException("Error while saving Bought_Comics");
		}
		String id = uri.toString();
		return Integer.valueOf(id);
    }

}
