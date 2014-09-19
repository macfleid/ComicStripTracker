package com.mcfly.cstracker.sqlite.dal;
 
import java.io.Serializable;
import java.util.Date;
/*
* AUTO GENERATED FILE 
* creation date : 2014-05-26 11:43 
*/
public class Collection_Has_BD implements Serializable { 

    public static String TABLE_NAME = "Collection_Has_BD"; 
    public final static String COLUMN_BD_ID="BD_id"; 
    public final static String COLUMN_COLLECTION_ID="Collection_id"; 
    public final static String COLUMN_ORDER="[order]"; 

    private int BD_id;
    private int Collection_id;
    private int order;

    public int getBD_id() { 
        return BD_id;
    }

    public void setBD_id(int obj) {
        this.BD_id = obj;
    }
    public int getCollection_id() { 
        return Collection_id;
    }

    public void setCollection_id(int obj) {
        this.Collection_id = obj;
    }
    public int getorder() { 
        return order;
    }

    public void setorder(int obj) {
        this.order = obj;
    }
}
