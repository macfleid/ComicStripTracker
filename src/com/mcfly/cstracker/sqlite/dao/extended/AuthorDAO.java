package com.mcfly.cstracker.sqlite.dao.extended;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.mcfly.cstracker.sqlite.contentprovider.AuthorContentProvider;
import com.mcfly.cstracker.sqlite.dao.BaseDAO;
import com.mcfly.cstracker.sqlite.dal.Author;
import com.mcfly.cstracker.sqlite.dao.extended.interfaces.IAuthor;

public class AuthorDAO extends BaseDAO implements IAuthor {

    private final static String TAG = AuthorDAO.class.getName();

    public AuthorDAO(Context c) {
        super(c, AuthorContentProvider.CONTENT_URI);
    }

}
