package com.mcfly.cstracker.adapters;

import java.util.Locale;

import com.mcfly.cstracker.R;
import com.mcfly.cstracker.fragments.LibraryFragment;
import com.mcfly.cstracker.fragments.ScannFragment;
import com.mcfly.cstracker.fragments.WatchListFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class SectionPagerAdapter extends FragmentStatePagerAdapter {
	
	private final static String TAG = SectionPagerAdapter.class.getName();
	
	private Context context;
	private String[] sections;
	
	private boolean forceRefresh;
	private int refreshedPosition;
	private Fragment refreshedFragment;
	
	public SectionPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
		sections = context.getResources().getStringArray(R.array.sections);
		forceRefresh = false;
	}

	@Override
	public Fragment getItem(int position) {
		Log.d(TAG, "[getItem]");
		Fragment fragment = null;
		if(forceRefresh && position==refreshedPosition) {
			Log.d(TAG, "   [forceRefresh] on position "+position);
			forceRefresh = false;
			return refreshedFragment;
		}
		switch (position) {
		case 0:
			fragment = new ScannFragment();
			break;
		case 1:
			fragment = new LibraryFragment();
			break;
		case 2:
			fragment = new WatchListFragment();
		}
		
		return fragment;
	}
	
	@Override
	public int getItemPosition(Object object) {
		Log.d(TAG, "[getItemPosition]");
		if(forceRefresh) {
			Log.d(TAG, " -->[POSITION_NONE]");
			return POSITION_NONE ;
		}
		return super .getItemPosition(object);
	}
	
	public void switchFragment(int position, Fragment fragment) {
		Log.d(TAG, "[switchFragment]");
		forceRefresh = true ;
		refreshedPosition = position;
		refreshedFragment = fragment;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return sections.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		return sections[position];
	}

}
