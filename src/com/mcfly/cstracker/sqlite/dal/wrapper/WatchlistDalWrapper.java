package com.mcfly.cstracker.sqlite.dal.wrapper;

import android.content.ContentValues;import java.io.Serializable;
import com.mcfly.cstracker.sqlite.dal.Watchlist;
import com.mcfly.cstracker.sqlite.cursor.WatchlistCursor;
import java.util.Date;

public class WatchlistDalWrapper {

    public static Watchlist getObjectFromDB(WatchlistCursor cursor, int start) { 
        Watchlist object_ = new Watchlist();
        object_.setBD__id(cursor.getInt(0+start));
        object_.setdate(cursor.getString(1+start));
        return object_;
    }

    public static int getNbColumns() { 
        return 2;
    }

    public static ContentValues getContentValueFromObject(Serializable object) { 
        Watchlist object_ = (Watchlist) object;
        ContentValues values = new ContentValues();
        values.put(Watchlist.COLUMN_BD__ID,object_.getBD__id());
        values.put(Watchlist.COLUMN_DATE,object_.getdate());
        return values;
    }

}