package com.mcfly.cstracker.sqlite.dao.extended;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.mcfly.cstracker.sqlite.contentprovider.BDContentProvider;
import com.mcfly.cstracker.sqlite.contentprovider.CollectionContentProvider;
import com.mcfly.cstracker.sqlite.dao.BaseDAO;
import com.mcfly.cstracker.sqlite.dal.BD;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.dal.wrapper.BDDalWrapper;
import com.mcfly.cstracker.sqlite.dao.extended.interfaces.IBD;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;

public class BDDAO extends BaseDAO implements IBD {

    private final static String TAG = BDDAO.class.getName();

    public BDDAO(Context c) {
        super(c, BDContentProvider.CONTENT_URI);
    }

    @Override
    public int save(BD bd) throws BaseDAOException {
    	Uri uri = context.getContentResolver()
    		.insert(contentProviderUri, BDDalWrapper.getContentValueFromObject(bd));
		if(uri==null) {
			throw new BaseDAOException("Error while saving comics");
		}
		String id = uri.toString();
		return Integer.valueOf(id);
    }
    
    @Override
    public Cursor getComics(Collection collection) {
		Cursor cursor = context.getContentResolver().query(
				BDContentProvider.BYCOLLECTION_URI,
				null,
				String.format("Collection_name='%s'", collection.getname()), 
				null, 
				null);
		return cursor;
	}
}
