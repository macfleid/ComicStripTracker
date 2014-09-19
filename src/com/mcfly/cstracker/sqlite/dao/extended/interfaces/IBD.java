package com.mcfly.cstracker.sqlite.dao.extended.interfaces;

import android.database.Cursor;

import com.mcfly.cstracker.sqlite.dal.BD;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;


public interface IBD{
	
	public int save(BD bd) throws BaseDAOException;
	
	public Cursor getComics(Collection collection);
}
