package com.mcfly.cstracker.sqlite.dao.extended;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.mcfly.cstracker.sqlite.contentprovider.Collection_Has_BDContentProvider;
import com.mcfly.cstracker.sqlite.cursor.CollectionCursor;
import com.mcfly.cstracker.sqlite.cursor.Collection_Has_BDCursor;
import com.mcfly.cstracker.sqlite.dao.BaseDAO;
import com.mcfly.cstracker.sqlite.dal.BD;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.dal.Collection_Has_BD;
import com.mcfly.cstracker.sqlite.dal.wrapper.CollectionDalWrapper;
import com.mcfly.cstracker.sqlite.dal.wrapper.Collection_Has_BDDalWrapper;
import com.mcfly.cstracker.sqlite.dao.extended.interfaces.ICollection_Has_BD;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;

public class Collection_Has_BDDAO extends BaseDAO implements ICollection_Has_BD {

    private final static String TAG = Collection_Has_BDDAO.class.getName();

    public Collection_Has_BDDAO(Context c) {
        super(c, Collection_Has_BDContentProvider.CONTENT_URI);
    }

	@Override
	public Collection_Has_BD get(Collection collection, int order) {
		Cursor cursor = context.getContentResolver().query(contentProviderUri, 
				null, 
				String.format("%s=%s and %s=%s", 
						Collection_Has_BD.COLUMN_COLLECTION_ID, collection.get_id(),
						Collection_Has_BD.COLUMN_ORDER, order), 
				null, 
				null);
		Collection_Has_BD result = null;
		if(cursor.moveToFirst()) {
			result = Collection_Has_BDDalWrapper.getObjectFromDB(new Collection_Has_BDCursor(cursor), 0);
		}
		return result;
	}
	
	@Override
	public Collection_Has_BD get(BD bd) {
		Cursor cursor = context.getContentResolver().query(contentProviderUri, 
				null, 
				String.format("%s=%s", 
						Collection_Has_BD.COLUMN_BD_ID, bd.get_id()), 
				null, 
				null);
		Collection_Has_BD result = null;
		if(cursor.moveToFirst()) {
			result = Collection_Has_BDDalWrapper.getObjectFromDB(new Collection_Has_BDCursor(cursor), 0);
		}
		return result;
	}

	@Override
	public int save(Collection_Has_BD object) throws BaseDAOException {
		Uri uri = context.getContentResolver()
				.insert(contentProviderUri, Collection_Has_BDDalWrapper.getContentValueFromObject(object));
		if(uri==null) {
			throw new BaseDAOException("Error while saving to Collection_Has_BD");
		}
		String id = uri.toString();
		return Integer.valueOf(id);
	}

}
