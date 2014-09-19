package com.mcfly.cstracker.sqlite.dao.extended.interfaces;

import com.mcfly.cstracker.sqlite.dal.BD;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.dal.Collection_Has_BD;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;


public interface ICollection_Has_BD{
	
	public Collection_Has_BD get(Collection collection, int order);
	
	public Collection_Has_BD get(BD bd);
	
	public int save(Collection_Has_BD object) throws BaseDAOException; 
}
