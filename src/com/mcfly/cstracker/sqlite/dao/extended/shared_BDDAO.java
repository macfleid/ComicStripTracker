package com.mcfly.cstracker.sqlite.dao.extended;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.mcfly.cstracker.sqlite.contentprovider.shared_BDContentProvider;
import com.mcfly.cstracker.sqlite.dao.BaseDAO;
import com.mcfly.cstracker.sqlite.dal.shared_BD;
import com.mcfly.cstracker.sqlite.dao.extended.interfaces.Ishared_BD;

public class shared_BDDAO extends BaseDAO implements Ishared_BD {

    private final static String TAG = shared_BDDAO.class.getName();

    public shared_BDDAO(Context c) {
        super(c, shared_BDContentProvider.CONTENT_URI);
    }

}
