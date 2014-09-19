package com.mcfly.cstracker.sqlite.dal;
 
import java.io.Serializable;
import java.util.Date;
/*
* AUTO GENERATED FILE 
* creation date : 2014-05-26 11:43 
*/
public class BD_has_Author implements Serializable { 

    public static String TABLE_NAME = "BD_has_Author"; 
    public final static String COLUMN_BD_ID="BD_id"; 
    public final static String COLUMN_AUTHOR_ID="Author_id"; 

    private int BD_id;
    private int Author_id;

    public int getBD_id() { 
        return BD_id;
    }

    public void setBD_id(int obj) {
        this.BD_id = obj;
    }
    public int getAuthor_id() { 
        return Author_id;
    }

    public void setAuthor_id(int obj) {
        this.Author_id = obj;
    }
}
