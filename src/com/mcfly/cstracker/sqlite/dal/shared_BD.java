package com.mcfly.cstracker.sqlite.dal;
 
import java.io.Serializable;
import java.util.Date;
/*
* AUTO GENERATED FILE 
* creation date : 2014-05-26 11:43 
*/
public class shared_BD implements Serializable { 

    public static String TABLE_NAME = "shared_BD"; 
    public final static String COLUMN_BD_ID="BD_id"; 
    public final static String COLUMN_CONTACT="contact"; 

    private int BD_id;
    private String contact;

    public int getBD_id() { 
        return BD_id;
    }

    public void setBD_id(int obj) {
        this.BD_id = obj;
    }
    public String getcontact() { 
        return contact;
    }

    public void setcontact(String obj) {
        this.contact = obj;
    }
}
