package com.mcfly.cstracker.sqlite.dao.extended.interfaces;

import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;


public interface ICollection{
	
	public Collection getCollection(String name);
	
	public int save(Collection object) throws BaseDAOException;
}
