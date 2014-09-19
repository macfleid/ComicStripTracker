package com.mcfly.cstracker.sqlite.dao.extended.interfaces;

import com.mcfly.cstracker.sqlite.dal.Watchlist;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;


public interface IWatchlist{
	public int save(Watchlist object) throws BaseDAOException;
}
