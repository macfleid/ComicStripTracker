package com.mcfly.cstracker.fragments;

import com.mcfly.cstracker.R;
import com.mcfly.cstracker.activities.IMainActivityListener;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.dao.extended.BDDAO;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class LibraryItemsFragment extends Fragment {
	
	public final static String KEY_COLLECTION = "KEY_COLLECTION";

	private IMainActivityListener listener;
	
	private View view;
	private Cursor cursor;
	private Collection collection;
	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.listener = (IMainActivityListener) activity;
		if(getArguments()!=null) {
			collection = (Collection) getArguments().getSerializable(KEY_COLLECTION);
			BDDAO bdDAO = new BDDAO(getActivity());
			cursor = bdDAO.getComics(collection);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_library_items,
				container, false);
		TextView collectionName = (TextView) view.findViewById(R.id.library_items_collection);
		ListView listCollection = (ListView) view.findViewById(R.id.collection_items_listview_items);
		
		collectionName.setText(collection.getname());
		String[] from = { "comics_name", "comic_order", "BdDate", "boughtDate"};
		int[] to = {R.id.collection_comic_name, R.id.library_tome_number, 
				R.id.collection_item_createddate, R.id.collection_item_boughtdate};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				getActivity(), R.layout.library_collection_item, cursor, from, to, 0);
		listCollection.setAdapter(adapter);
		
		setReturnAction(collectionName);
		registerForContextMenu(listCollection);
		
		return view;
	}
	
	private void setReturnAction(View view) {
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.bindBackLibrary();
			}
		});
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.library_options, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return super.onContextItemSelected(item);
	}

	
}
