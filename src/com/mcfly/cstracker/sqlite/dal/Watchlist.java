package com.mcfly.cstracker.sqlite.dal;
 
import java.io.Serializable;
import java.util.Date;
/*
* AUTO GENERATED FILE 
* creation date : 2014-05-26 11:43 
*/
public class Watchlist implements Serializable { 

    public static String TABLE_NAME = "Watchlist"; 
    public final static String COLUMN_BD__ID="BD__id"; 
    public final static String COLUMN_DATE="date"; 

    private int BD__id;
    private String date;

    public int getBD__id() { 
        return BD__id;
    }

    public void setBD__id(int obj) {
        this.BD__id = obj;
    }
    public String getdate() { 
        return date;
    }

    public void setdate(String obj) {
        this.date = obj;
    }
}
