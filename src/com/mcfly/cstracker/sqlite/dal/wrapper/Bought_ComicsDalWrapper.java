package com.mcfly.cstracker.sqlite.dal.wrapper;

import android.content.ContentValues;import java.io.Serializable;
import com.mcfly.cstracker.sqlite.dal.Bought_Comics;
import com.mcfly.cstracker.sqlite.cursor.Bought_ComicsCursor;
import com.mcfly.cstracker.util.DateGetter;

import java.util.Date;

public class Bought_ComicsDalWrapper {

    public static Bought_Comics getObjectFromDB(Bought_ComicsCursor cursor, int start) { 
        Bought_Comics object_ = new Bought_Comics();
        object_.setBD__id(cursor.getInt(0+start));
        Date date = DateGetter.getInstance().getDateFromString(cursor.getString(1+start));
        object_.setdate(date);
        return object_;
    }

    public static int getNbColumns() { 
        return 2;
    }

    public static ContentValues getContentValueFromObject(Serializable object) { 
        Bought_Comics object_ = (Bought_Comics) object;
        ContentValues values = new ContentValues();
        values.put(Bought_Comics.COLUMN_BD__ID,object_.getBD__id());
        String dateString = DateGetter.getInstance().getStringFromDate(object_.getdate());
        values.put(Bought_Comics.COLUMN_DATE,dateString);
        return values;
    }

}