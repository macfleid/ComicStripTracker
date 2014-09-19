package com.mcfly.cstracker.sqlite.cursor;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.database.CursorWrapper;

public class BDCursor extends CursorWrapper {


    public BDCursor(Cursor c) {
        super(c);
    }

}