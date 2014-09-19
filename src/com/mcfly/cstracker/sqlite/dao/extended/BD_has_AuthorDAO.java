package com.mcfly.cstracker.sqlite.dao.extended;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.mcfly.cstracker.sqlite.contentprovider.BD_has_AuthorContentProvider;
import com.mcfly.cstracker.sqlite.dao.BaseDAO;
import com.mcfly.cstracker.sqlite.dal.BD_has_Author;
import com.mcfly.cstracker.sqlite.dao.extended.interfaces.IBD_has_Author;

public class BD_has_AuthorDAO extends BaseDAO implements IBD_has_Author {

    private final static String TAG = BD_has_AuthorDAO.class.getName();

    public BD_has_AuthorDAO(Context c) {
        super(c, BD_has_AuthorContentProvider.CONTENT_URI);
    }

}
