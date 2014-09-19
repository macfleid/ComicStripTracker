package com.mcfly.cstracker.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mcfly.cstracker.R;
import com.mcfly.cstracker.activities.IMainActivityListener;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.dao.extended.CollectionDAO;

public class LibraryFragment extends Fragment {
	
	private final static String TAG = ScannFragment.class.getName();

	private View view;
	private IMainActivityListener listener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.listener = (IMainActivityListener) activity;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_library_collections,
				container, false);
		ListView listCollection = (ListView) view.findViewById(R.id.collection_listview);
		
		CollectionDAO collectionDAO = new CollectionDAO(getActivity());
		Cursor cursor = collectionDAO.getLibraryCursor();
//		String[] from = {Collection.COLUMN_NAME, "collectionCount"};
		String[] from = {"collectionName", "collectionCount"};
		int[] to = { R.id.library_list_collection, R.id.library_list_nbcomics };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				getActivity(), R.layout.library_list_item, cursor, from, to, 0);

		listCollection.setAdapter(adapter);
		setOnItemClickListener(listCollection);
		
		return view;
	}
	
	private void setOnItemClickListener(ListView listCollection) {
		listCollection.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TextView collectionName = (TextView) view.findViewById(R.id.library_list_collection);
				Collection collection = null;
				CollectionDAO collectionDAO = new CollectionDAO(getActivity());
				collection = collectionDAO.getCollection(collectionName.getText().toString());
				if(collection != null) {
					listener.bindLibraryCollection(collection);
				}
			}
		});
	}
	
}
