package com.mcfly.cstracker.sqlite.dao;

import java.io.Serializable;

import com.mcfly.cstracker.sqlite.exception.BaseDAOException;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public abstract class BaseDAO implements IBaseDAO {
	private final static String TAG = BaseDAO.class.getName();
	
	protected Context context;
	protected Uri contentProviderUri;

	public BaseDAO(Context c, Uri contentProviderUri) {
		this.context = c;
		this.contentProviderUri = contentProviderUri;
	}

	@Override
	public Cursor getFromID(int id, String column) {
		Cursor cursor = context.getContentResolver().query(contentProviderUri,
                null, column+"="+id,null,null);
        return cursor;
	}

	@Override
	public Cursor findAll() {
		Cursor cursor = context.getContentResolver().query(contentProviderUri,
                null, null,null,null);
		return cursor;
	}

	@Override
	public int delete(int[] ids, String column) {
		String where = column+" IN (";
		for (int id : ids ) {
			where+= id;
			if (ids[ids.length-1]!=id) {
				where+= ",";
			}
		}
		where += ")";
		int result = context.getContentResolver().delete(contentProviderUri, where, null);
		return result;
	}

	@Override
	public int add(ContentValues values) throws BaseDAOException {
		Uri uri = context.getContentResolver().insert(contentProviderUri, values);
		if(uri!=null) {
			String id = uri.toString();
			return Integer.valueOf(id);
		}
		return -1;
	}
	
}
