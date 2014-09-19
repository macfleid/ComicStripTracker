package com.mcfly.cstracker.sqlite.dal.wrapper;

import android.content.ContentValues;import java.io.Serializable;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.cursor.CollectionCursor;
import java.util.Date;

public class CollectionDalWrapper {

    public static Collection getObjectFromDB(CollectionCursor cursor, int start) { 
        Collection object_ = new Collection();
        object_.set_id(cursor.getInt(0+start));
        object_.setname(cursor.getString(1+start));
        return object_;
    }

    public static int getNbColumns() { 
        return 2;
    }

    public static ContentValues getContentValueFromObject(Serializable object) { 
        Collection object_ = (Collection) object;
        ContentValues values = new ContentValues();
        values.put(Collection.COLUMN_NAME,object_.getname());
        return values;
    }

}