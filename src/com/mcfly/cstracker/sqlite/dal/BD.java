package com.mcfly.cstracker.sqlite.dal;
 
import java.io.Serializable;
import java.util.Date;
/*
* AUTO GENERATED FILE 
* creation date : 2014-05-26 11:43 
*/
public class BD implements Serializable { 

    public static String TABLE_NAME = "BD"; 
    public final static String COLUMN__ID="_id"; 
    public final static String COLUMN_NAME="name"; 
    public final static String COLUMN_DATE="date"; 
    public final static String COLUMN_ISBN="isbn"; 
    public final static String COLUMN_LANGUAGE="language"; 

    private int _id;
    private String name;
    private Date date;
    private String isbn;
    private String language;

    public int get_id() { 
        return _id;
    }

    public void set_id(int obj) {
        this._id = obj;
    }
    public String getname() { 
        return name;
    }

    public void setname(String obj) {
        this.name = obj;
    }
    public Date getdate() { 
        return date;
    }

    public void setdate(Date obj) {
        this.date = obj;
    }
    public String getisbn() { 
        return isbn;
    }

    public void setisbn(String obj) {
        this.isbn = obj;
    }
    public String getlanguage() { 
        return language;
    }

    public void setlanguage(String obj) {
        this.language = obj;
    }
}
