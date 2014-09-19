package com.mcfly.cstracker.business;

import java.util.Calendar;
import java.util.Date;

import com.mcfly.cstracker.business.exception.BusinessException;
import com.mcfly.cstracker.sqlite.DbManager;
import com.mcfly.cstracker.sqlite.dal.BD;
import com.mcfly.cstracker.sqlite.dal.Bought_Comics;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.dal.Collection_Has_BD;
import com.mcfly.cstracker.sqlite.dal.Watchlist;
import com.mcfly.cstracker.sqlite.dao.extended.BDDAO;
import com.mcfly.cstracker.sqlite.dao.extended.Bought_ComicsDAO;
import com.mcfly.cstracker.sqlite.dao.extended.CollectionDAO;
import com.mcfly.cstracker.sqlite.dao.extended.Collection_Has_BDDAO;
import com.mcfly.cstracker.sqlite.dao.extended.WatchlistDAO;
import com.mcfly.cstracker.sqlite.exception.BaseDAOException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ScannResultBusiness {

	private final static String TAG = ScannResultBusiness.class.getName();
	
	private Context context;
	private DbManager dbManager;
	
	public ScannResultBusiness(Context context) {
		this.context = context;
		dbManager = DbManager.getInstance(context);
	}
	
	
	public void saveNewComic(String collection, int order, String name, String year, 
			String isbn, String language, boolean bought) throws BusinessException {
		SQLiteDatabase database = dbManager.getWritableDatabase();
		database.beginTransaction();
		try {
			BD bd = saveNewComic(name, year, isbn, language);
			if(bought) {
				saveComicsToBought(bd);
			} else {
				saveComicsToWatchList(bd);
			}
			saveToCollection(bd, order, collection);
			database.setTransactionSuccessful();
			Log.d(TAG, "save sucessFull on title:"+bd.getname());
			Log.d(TAG, "				   collection:"+collection);
		} catch (BaseDAOException e) {
			Log.e(TAG, "Error while adding comic", e);
			throw new BusinessException("Error while saveNewComic",e);
		} catch (BusinessException e) {
			Log.e(TAG, "Error while adding comic", e);
			throw new BusinessException("Error while saveNewComic",e);
		} finally {
			database.endTransaction();
		}
	}
	
	
	//-----------------------------
	//-- PRIVATES 
	//-----------------------------
	
	private BD saveNewComic(String name, String year, String isbn, String language) throws BaseDAOException {
		BD bd = new BD();
		bd.setname(name);
		bd.setlanguage(language);
		bd.setisbn(isbn);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.valueOf(year));
		bd.setdate(calendar.getTime());
		
		//-- TODO check isbn in db => throw new BusinessException()
		
		BDDAO dao = new BDDAO(context);
		int id = dao.save(bd);
		bd.set_id(id);
		return bd;
	}
	
	private void saveComicsToBought(BD bd) throws BaseDAOException {
		Bought_Comics boughtComic = new Bought_Comics();
		boughtComic.setBD__id(bd.get_id());
		boughtComic.setdate(new Date());
		
		Bought_ComicsDAO dao = new Bought_ComicsDAO(context);
		dao.save(boughtComic);
	}

	private void saveComicsToWatchList(BD bd) throws BaseDAOException {
		Watchlist watchList = new Watchlist();
		watchList.setBD__id(bd.get_id());
		watchList.setdate(new Date().toString()); //FIXME
		
		WatchlistDAO dao = new WatchlistDAO(context);
		dao.save(watchList);
	}
	
	private void saveToCollection(BD bd, int order, String collectionName) 
			throws BaseDAOException, BusinessException {
		CollectionDAO collectionDAO = new CollectionDAO(context);
		Collection_Has_BDDAO collectionHasBDDAO = new Collection_Has_BDDAO(context);
		Collection_Has_BD collectionHasBd = null;
		Collection collection = collectionDAO.getCollection(collectionName);
		if (collection == null) {
			collection = new Collection();
			collection.setname(collectionName);
			collection.set_id(collectionDAO.save(collection));
			Log.d(TAG, "...New collection saved:"+collection);
		} else {
			if(collectionHasBDDAO.get(collection, order)!=null) {
				throw new BusinessException("this collection already have this tome number");
			}
			if(collectionHasBDDAO.get(bd)!=null) {
				throw new BusinessException("this comics is already saved to a collection");
			}
		}
		
		collectionHasBd = new Collection_Has_BD();
		collectionHasBd.setBD_id(bd.get_id());
		collectionHasBd.setCollection_id(collection.get_id());
		collectionHasBd.setorder(order);
		collectionHasBDDAO.save(collectionHasBd);
	}
}
