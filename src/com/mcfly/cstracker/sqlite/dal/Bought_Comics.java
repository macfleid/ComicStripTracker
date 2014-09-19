package com.mcfly.cstracker.sqlite.dal;
 
import java.io.Serializable;
import java.util.Date;
/*
* AUTO GENERATED FILE 
* creation date : 2014-05-26 11:43 
*/
public class Bought_Comics implements Serializable { 

    public static String TABLE_NAME = "Bought_Comics"; 
    public final static String COLUMN_BD__ID="BD__id"; 
    public final static String COLUMN_DATE="date"; 

    private int BD__id;
    private Date date;

    public int getBD__id() { 
        return BD__id;
    }

    public void setBD__id(int obj) {
        this.BD__id = obj;
    }
    public Date getdate() { 
        return date;
    }

    public void setdate(Date obj) {
        this.date = obj;
    }
}
