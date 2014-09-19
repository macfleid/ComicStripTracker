package com.mcfly.cstracker.sqlite.dal.wrapper;

import android.content.ContentValues;import java.io.Serializable;
import com.mcfly.cstracker.sqlite.dal.Author;
import com.mcfly.cstracker.sqlite.cursor.AuthorCursor;
import java.util.Date;

public class AuthorDalWrapper {

    public static Author getObjectFromDB(AuthorCursor cursor, int start) { 
        Author object_ = new Author();
        object_.set_id(cursor.getInt(0+start));
        object_.setname(cursor.getString(1+start));
        return object_;
    }

    public static int getNbColumns() { 
        return 2;
    }

    public static ContentValues getContentValueFromObject(Serializable object) { 
        Author object_ = (Author) object;
        ContentValues values = new ContentValues();
        values.put(Author.COLUMN__ID,object_.get_id());
        values.put(Author.COLUMN_NAME,object_.getname());
        return values;
    }

}