package com.mcfly.cstracker.sqlite.dal.wrapper;

import android.content.ContentValues;import java.io.Serializable;
import com.mcfly.cstracker.sqlite.dal.Collection_Has_BD;
import com.mcfly.cstracker.sqlite.cursor.Collection_Has_BDCursor;
import java.util.Date;

public class Collection_Has_BDDalWrapper {

    public static Collection_Has_BD getObjectFromDB(Collection_Has_BDCursor cursor, int start) { 
        Collection_Has_BD object_ = new Collection_Has_BD();
        object_.setBD_id(cursor.getInt(0+start));
        object_.setCollection_id(cursor.getInt(1+start));
        object_.setorder(cursor.getInt(2+start));
        return object_;
    }

    public static int getNbColumns() { 
        return 3;
    }

    public static ContentValues getContentValueFromObject(Serializable object) { 
        Collection_Has_BD object_ = (Collection_Has_BD) object;
        ContentValues values = new ContentValues();
        values.put(Collection_Has_BD.COLUMN_BD_ID,object_.getBD_id());
        values.put(Collection_Has_BD.COLUMN_COLLECTION_ID,object_.getCollection_id());
        values.put(Collection_Has_BD.COLUMN_ORDER,object_.getorder());
        return values;
    }

}