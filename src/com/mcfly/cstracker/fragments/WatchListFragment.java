package com.mcfly.cstracker.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mcfly.cstracker.R;
import com.mcfly.cstracker.activities.IMainActivityListener;
import com.mcfly.cstracker.business.WatchListBusiness;
import com.mcfly.cstracker.business.exception.BusinessException;
import com.mcfly.cstracker.sqlite.dal.Author;
import com.mcfly.cstracker.sqlite.dal.BD;
import com.mcfly.cstracker.sqlite.dao.extended.CollectionDAO;
import com.mcfly.cstracker.sqlite.dao.extended.WatchlistDAO;
import com.mcfly.cstracker.sync.model.IsbnModel;
import com.mcfly.cstracker.sync.model.IsbnProperties;
import com.mcfly.cstracker.util.KLog;

public class WatchListFragment extends Fragment {
	
	private final static String TAG = WatchListFragment.class.getName();

	private View view;
	private Cursor cursor;
	private IMainActivityListener listener;
	private WatchListBusiness business;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.listener = (IMainActivityListener) activity;
		business = new WatchListBusiness(activity);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_library_collections,
				container, false);
		ListView listCollection = (ListView) view.findViewById(R.id.collection_listview);
		
		WatchlistDAO watchlistDAO = new WatchlistDAO(getActivity());
		cursor = watchlistDAO.getLibraryCursor();
		String[] from = {"Collection_name", "comics_name", "comic_order", "BdDate", "boughtDate"};
		int[] to = {R.id.collection_comic_collection, R.id.collection_comic_name, R.id.library_tome_number, 
				R.id.collection_item_createddate, R.id.collection_item_boughtdate};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				getActivity(), R.layout.watchlist_item, cursor, from, to, 0);
		listCollection.setAdapter(adapter);
		registerForContextMenu(listCollection);
		return view;
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.watchlist_options, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		int selectpos = menuinfo.position;
		KLog.d(getActivity(), TAG, " position:"+selectpos);
		ListView listCollection = (ListView) view.findViewById(R.id.collection_listview);
		long itemId = listCollection.getAdapter().getItemId(selectpos);
		
		switch (item.getItemId()) {
		case R.id.action_watch_delete:
			KLog.d(getActivity(), TAG, " delete id:"+itemId);
			business.deleteItem((int)itemId);
			refreshList();
			break;
		case R.id.action_set_to_bought:
			try {
				business.setAsBought((int)itemId);
			} catch (BusinessException e) {
				KLog.e(getActivity(), TAG, "[setAsBought ERROR]",e);
			}
			refreshList();
			break;
		case R.id.action_watch_edit:
			edit((int)itemId);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	
	private void refreshList() {
		ListView listCollection = (ListView) view.findViewById(R.id.collection_listview);
		WatchlistDAO watchlistDAO = new WatchlistDAO(getActivity());
		cursor = watchlistDAO.getLibraryCursor();
		((SimpleCursorAdapter) listCollection.getAdapter()).swapCursor(cursor);
		((SimpleCursorAdapter) listCollection.getAdapter()).notifyDataSetChanged();
		((SimpleCursorAdapter) listCollection.getAdapter()).notifyDataSetInvalidated();
		listCollection.invalidate();
	}
	
	private void edit(int bdId) {
		BD bd = business.getBd(bdId);
		Author author = business.getBdAuthor(bdId);
		IsbnModel model = new IsbnModel();
		List<IsbnProperties> list = new ArrayList<IsbnProperties>();
		IsbnProperties prop = new IsbnProperties();
		list.add(prop);
		model.setList(list);
		
		prop.setAuthor(author.getname());
		prop.setTitle(bd.getname());
		prop.setYear("2014"); //FIXME
		prop.setPublisher("");
	}
	
}
