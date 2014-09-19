package com.mcfly.cstracker.business;

import java.util.Date;

import com.mcfly.cstracker.business.exception.BusinessException;
import com.mcfly.cstracker.sqlite.cursor.BDCursor;
import com.mcfly.cstracker.sqlite.dal.Author;
import com.mcfly.cstracker.sqlite.dal.BD;
import com.mcfly.cstracker.sqlite.dal.Bought_Comics;
import com.mcfly.cstracker.sqlite.dal.Collection_Has_BD;
import com.mcfly.cstracker.sqlite.dal.Watchlist;
import com.mcfly.cstracker.sqlite.dal.wrapper.BDDalWrapper;
import com.mcfly.cstracker.sqlite.dao.extended.BDDAO;
import com.mcfly.cstracker.sqlite.dao.extended.Bought_ComicsDAO;
import com.mcfly.cstracker.sqlite.dao.extended.Collection_Has_BDDAO;
import com.mcfly.cstracker.sqlite.dao.extended.WatchlistDAO;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;

import android.content.Context;
import android.database.Cursor;

public class WatchListBusiness {

	private Context context;
	
	public WatchListBusiness(Context context) {
		this.context = context;
	}
	
	public void deleteItem(int comic_id) {
		if(comic_id==-1 || comic_id==0) {
			return;
		}
		int[] ids = {comic_id}; 
		BDDAO bdDao = new BDDAO(context);
		WatchlistDAO watchListDAO = new WatchlistDAO(context);
		Collection_Has_BDDAO collectionHasBdDAO = new Collection_Has_BDDAO(context);
		bdDao.delete(ids, BD.COLUMN__ID);
		watchListDAO.delete(ids, Watchlist.COLUMN_BD__ID);
		collectionHasBdDAO.delete(ids, Collection_Has_BD.COLUMN_BD_ID);
	}
	
	public void setAsBought(int comic_id) throws BusinessException  {
		if(comic_id==-1 || comic_id==0) {
			return;
		}
		int[] ids = {comic_id}; 
		Bought_ComicsDAO boughtDAO = new Bought_ComicsDAO(context);
		WatchlistDAO watchListDAO = new WatchlistDAO(context);
		watchListDAO.delete(ids, Watchlist.COLUMN_BD__ID);
		
		Bought_Comics boughtComic = new Bought_Comics();
		boughtComic.setBD__id(comic_id);
		boughtComic.setdate(new Date());
		int result;
		try {
			result = boughtDAO.save(boughtComic);
		} catch (BaseDAOException e) {
			throw new BusinessException("Error while switching to library",e);
		}
		if(result == -1) {
			throw new BusinessException("Error while switching to library");
		}
	}
	
	public BD getBd(int id) {
		BDDAO dao = new BDDAO(context);
		Cursor c = dao.getFromID(id, BD.COLUMN__ID);
		c.moveToFirst();
		BD result = BDDalWrapper.getObjectFromDB(new BDCursor(c), 0);
		c.close();
		return result;
	}
	
	public Author getBdAuthor(int id) {
		BDDAO dao = new BDDAO(context);
		
		return null;
	}
	
}
