package com.mcfly.cstracker.sqlite.dao.extended.interfaces;

import com.mcfly.cstracker.sqlite.dal.Bought_Comics;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;


public interface IBought_Comics{
	
	public int save(Bought_Comics object) throws BaseDAOException;
}
