package com.mcfly.cstracker.activities;

import java.util.Locale;

import com.mcfly.cstracker.R;
import com.mcfly.cstracker.adapters.SectionPagerAdapter;
import com.mcfly.cstracker.fragments.LibraryFragment;
import com.mcfly.cstracker.fragments.LibraryItemsFragment;
import com.mcfly.cstracker.fragments.ScannFragment;
import com.mcfly.cstracker.fragments.ScannResultFragment;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sync.model.IsbnModel;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener, IMainActivityListener {
	
	private ViewPager mViewPager;
	private SectionPagerAdapter mSectionsPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionPagerAdapter(this,getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_scann:
			bindScann();
			Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
			intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "SCAN_MODE");
            startActivityForResult(intent, 0);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}


	@Override
	public void onBackPressed() {
		bindScann();
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}


	//-------------------------------------------------------------------------
	//-- IMainActivityListener
	//-------------------------------------------------------------------------
	
	@Override
	public void bindIsbnResult(IsbnModel isbnModel) {
		mViewPager.setCurrentItem(1);
		Fragment fragment = new ScannResultFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(ScannResultFragment.KEY_ISBNMODEL, isbnModel);
		fragment.setArguments(bundle);
		mSectionsPagerAdapter.switchFragment(0, fragment);
		mViewPager.setCurrentItem(0);
	}

	@Override
	public void bindWaitUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bindLibrary() {
		mSectionsPagerAdapter.notifyDataSetChanged();
		mViewPager.setCurrentItem(1);
		mSectionsPagerAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void bindBackLibrary() {
		mViewPager.setCurrentItem(0);
		Fragment fragment = new LibraryFragment();
		mSectionsPagerAdapter.switchFragment(1, fragment);
		mViewPager.setCurrentItem(1);
	}

	@Override
	public void bindScann() {
		mViewPager.setCurrentItem(1);
		Fragment fragment = new ScannFragment();
		mSectionsPagerAdapter.switchFragment(0, fragment);
		mViewPager.setCurrentItem(0);
	}

	@Override
	public void bindLibraryCollection(Collection collection) {
		mViewPager.setCurrentItem(0);
		LibraryItemsFragment fragment = new LibraryItemsFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(LibraryItemsFragment.KEY_COLLECTION, collection);
		fragment.setArguments(bundle);
		mSectionsPagerAdapter.switchFragment(1, fragment);
		mViewPager.setCurrentItem(1);
	}
	

}
