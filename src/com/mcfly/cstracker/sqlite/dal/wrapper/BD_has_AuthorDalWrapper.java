package com.mcfly.cstracker.sqlite.dal.wrapper;

import android.content.ContentValues;import java.io.Serializable;
import com.mcfly.cstracker.sqlite.dal.BD_has_Author;
import com.mcfly.cstracker.sqlite.cursor.BD_has_AuthorCursor;
import java.util.Date;

public class BD_has_AuthorDalWrapper {

    public static BD_has_Author getObjectFromDB(BD_has_AuthorCursor cursor, int start) { 
        BD_has_Author object_ = new BD_has_Author();
        object_.setBD_id(cursor.getInt(0+start));
        object_.setAuthor_id(cursor.getInt(1+start));
        return object_;
    }

    public static int getNbColumns() { 
        return 2;
    }

    public static ContentValues getContentValueFromObject(Serializable object) { 
        BD_has_Author object_ = (BD_has_Author) object;
        ContentValues values = new ContentValues();
        values.put(BD_has_Author.COLUMN_BD_ID,object_.getBD_id());
        values.put(BD_has_Author.COLUMN_AUTHOR_ID,object_.getAuthor_id());
        return values;
    }

}