package com.mcfly.cstracker.sqlite.dao;

import java.io.Serializable;

import com.mcfly.cstracker.sqlite.exception.BaseDAOException;

import android.content.ContentValues;
import android.database.Cursor;

public interface IBaseDAO {
	
	public Cursor getFromID(int id,String column);
	
	public Cursor findAll();
	
	public int delete(int[] ids, String column);
	
	public int add(ContentValues values) throws BaseDAOException;
	

}
