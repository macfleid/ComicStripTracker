package com.mcfly.cstracker.sqlite.dal.wrapper;

import android.content.ContentValues;import java.io.Serializable;
import com.mcfly.cstracker.sqlite.dal.shared_BD;
import com.mcfly.cstracker.sqlite.cursor.shared_BDCursor;
import java.util.Date;

public class shared_BDDalWrapper {

    public static shared_BD getObjectFromDB(shared_BDCursor cursor, int start) { 
        shared_BD object_ = new shared_BD();
        object_.setBD_id(cursor.getInt(0+start));
        object_.setcontact(cursor.getString(1+start));
        return object_;
    }

    public static int getNbColumns() { 
        return 2;
    }

    public static ContentValues getContentValueFromObject(Serializable object) { 
        shared_BD object_ = (shared_BD) object;
        ContentValues values = new ContentValues();
        values.put(shared_BD.COLUMN_BD_ID,object_.getBD_id());
        values.put(shared_BD.COLUMN_CONTACT,object_.getcontact());
        return values;
    }

}