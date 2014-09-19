package com.mcfly.cstracker.sqlite.dal.wrapper;

import android.content.ContentValues;import java.io.Serializable;
import com.mcfly.cstracker.sqlite.dal.BD;
import com.mcfly.cstracker.sqlite.cursor.BDCursor;
import com.mcfly.cstracker.util.DateGetter;

import java.util.Date;

public class BDDalWrapper {

    public static BD getObjectFromDB(BDCursor cursor, int start) { 
        BD object_ = new BD();
        object_.set_id(cursor.getInt(0+start));
        object_.setname(cursor.getString(1+start));
        Date date = DateGetter.getInstance().getDateFromString(cursor.getString(2+start));
        object_.setdate(date);
        object_.setisbn(cursor.getString(3+start));
        object_.setlanguage(cursor.getString(4+start));
        return object_;
    }

    public static int getNbColumns() { 
        return 5;
    }

    public static ContentValues getContentValueFromObject(Serializable object) { 
        BD object_ = (BD) object;
        ContentValues values = new ContentValues();
        values.put(BD.COLUMN_NAME,object_.getname());
        String dateString = DateGetter.getInstance().getStringFromDate(object_.getdate());
        values.put(BD.COLUMN_DATE,dateString);
        values.put(BD.COLUMN_ISBN,object_.getisbn());
        values.put(BD.COLUMN_LANGUAGE,object_.getlanguage());
        return values;
    }

}