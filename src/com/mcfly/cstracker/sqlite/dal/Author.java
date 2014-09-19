package com.mcfly.cstracker.sqlite.dal;
 
import java.io.Serializable;
import java.util.Date;
/*
* AUTO GENERATED FILE 
* creation date : 2014-05-26 11:43 
*/
public class Author implements Serializable { 

    public static String TABLE_NAME = "Author"; 
    public final static String COLUMN__ID="_id"; 
    public final static String COLUMN_NAME="name"; 

    private int _id;
    private String name;

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
}
