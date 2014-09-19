package com.mcfly.cstracker.sqlite.cursor;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.database.CursorWrapper;

public class AuthorCursor extends CursorWrapper {


    public AuthorCursor(Cursor c) {
        super(c);
    }

}