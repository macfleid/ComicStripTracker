package com.mcfly.cstracker.sqlite.cursor;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.database.CursorWrapper;

public class shared_BDCursor extends CursorWrapper {


    public shared_BDCursor(Cursor c) {
        super(c);
    }

}