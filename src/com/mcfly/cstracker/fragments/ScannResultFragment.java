package com.mcfly.cstracker.fragments;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mcfly.cstracker.R;
import com.mcfly.cstracker.activities.IMainActivityListener;
import com.mcfly.cstracker.business.ScannResultBusiness;
import com.mcfly.cstracker.business.exception.BusinessException;
import com.mcfly.cstracker.scann.action.IDataSyncFinished;
import com.mcfly.cstracker.sqlite.cursor.CollectionCursor;
import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sqlite.dal.wrapper.CollectionDalWrapper;
import com.mcfly.cstracker.sqlite.dao.extended.CollectionDAO;
import com.mcfly.cstracker.sync.model.IsbnModel;
import com.mcfly.cstracker.sync.model.IsbnProperties;
import com.mcfly.cstracker.util.DateGetter;
import com.mcfly.cstracker.util.KLog;

public class ScannResultFragment extends Fragment {
	
	private final static String TAG = ScannFragment.class.getName();
	
	public final static String KEY_ISBNMODEL = "KEY_ISBNMODEL";
	public final static String KEY_EDIT_MODE = "KEY_EDIT_MODE";
	
	private View view;
	private Calendar calendar;
	private IMainActivityListener listener;
	
	private IsbnModel isbnModel;
	private ScannResultBusiness business;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.listener = (IMainActivityListener) activity;
		calendar = Calendar.getInstance();
		business = new ScannResultBusiness(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_scann_result,
				container, false);
		EditText tomeName = (EditText) view.findViewById(R.id.tome_name);
		EditText tomeNumber = (EditText) view.findViewById(R.id.tome_number);
		EditText tomeEdition = (EditText) view.findViewById(R.id.tome_edition);
		EditText parutionDate = (EditText) view.findViewById(R.id.tome_parution_date);
		Spinner actionsSpinner = (Spinner) view.findViewById(R.id.spinner_result_action);
		Spinner collectionSpinner = (Spinner) view.findViewById(R.id.spinner_collection);
		Button validateButton = (Button) view.findViewById(R.id.button_validate);
		Button cancelButton = (Button) view.findViewById(R.id.button_cancel);
		
		if(getArguments()!=null && getArguments().containsKey(KEY_ISBNMODEL)) {
			isbnModel = (IsbnModel) getArguments().getSerializable(KEY_ISBNMODEL);
			if(isbnModel.getList()!=null && isbnModel.getList().size()>0) {
				IsbnProperties properties = isbnModel.getList().get(0);
				tomeName.setText(properties.getTitle());
				tomeEdition.setText(properties.getPublisher());
				parutionDate.setText(properties.getYear());
			}
		}
		
		//-- init Actions Spinner
		String[] actionsArray = getResources().getStringArray(R.array.scann_result_actions);
		ArrayAdapter<String> actionsAdapter = new ArrayAdapter<String>(getActivity(), 
				R.layout.spinner_items, actionsArray);
		actionsSpinner.setAdapter(actionsAdapter);
		
		//--- Init Collection Spinner
		CollectionDAO collectionDAO = new CollectionDAO(getActivity());
		Cursor collectionsCursor = collectionDAO.findAll();
		
		String createCollectionLabel = getResources().getString(R.string.create_collection);
		MatrixCursor cursorActions = new MatrixCursor(
				new String[] {Collection.COLUMN__ID,Collection.COLUMN_NAME});
		cursorActions.addRow(new String[] {"0", createCollectionLabel});
		
		MergeCursor mergedCursor = new MergeCursor(new Cursor[] {collectionsCursor,cursorActions});
		
		String[] from = {Collection.COLUMN_NAME};
		int[] to = {android.R.id.text1};
		SimpleCursorAdapter collectionAdapter = new SimpleCursorAdapter(getActivity(), 
				R.layout.spinner_items, mergedCursor, from, to, 0);
		
		collectionSpinner.setAdapter(collectionAdapter);
		setOnCollectionSelected(collectionSpinner, collectionAdapter);
		//-----------
		
		setValidateAction(validateButton);
		setCancelAction(cancelButton);
		openCalendarAction(parutionDate);
		
		return view;
	}
	
	private void setCancelAction(Button button) {
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.bindScann();
			}
		});
	}
	
	private void setValidateAction(Button button) {
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//-- TODO interdire collection createCollectionLabel
				EditText tomeName = (EditText) view.findViewById(R.id.tome_name);
				EditText tomeNumber = (EditText) view.findViewById(R.id.tome_number);
				EditText tomeEdition = (EditText) view.findViewById(R.id.tome_edition);
				EditText parutionDate = (EditText) view.findViewById(R.id.tome_parution_date);
				Spinner actionsSpinner = (Spinner) view.findViewById(R.id.spinner_result_action);
				Spinner collectionSpinner = (Spinner) view.findViewById(R.id.spinner_collection);
				String[] actionsArray = getResources().getStringArray(R.array.scann_result_actions);
				String createCollectionLabel = getResources().getString(R.string.create_collection);
				EditText createdCollection = (EditText) view.findViewById(R.id.create_collection);
				
				String collection = null;
				Cursor item = (Cursor) collectionSpinner.getSelectedItem();
				Collection collection_ = CollectionDalWrapper.getObjectFromDB(new CollectionCursor(item), 0);
				if(collection_.getname().equals(createCollectionLabel)) {
					collection = createdCollection.getText().toString();
				} else {
					collection = collection_.getname();
				}
				
				if(collection==null || collection.isEmpty()) {
					Toast.makeText(getActivity(), "No collection selected", Toast.LENGTH_SHORT).show();
					return;
				}
				if(tomeName.getText()==null || tomeName.getText().toString().isEmpty()) {
					Toast.makeText(getActivity(), "Enter a comic title", Toast.LENGTH_SHORT).show();
					return;
				}
				
				int order = Integer.valueOf(tomeNumber.getText().toString());
				String name = tomeName.getText().toString();
				String year = parutionDate.getText().toString();
				String isbn = isbnModel.getList()!=null ? isbnModel.getList().get(0).getIsbn().get(0): "--";
				String language = isbnModel.getList()!=null ? isbnModel.getList().get(0).getLang() : "--";
				
				boolean bought = actionsSpinner.getSelectedItem().equals(actionsArray[0]);
				
				try {
					business.saveNewComic(collection, order, name, year, isbn, language, bought);
				} catch (BusinessException e) {
					Toast.makeText(getActivity(), 
							"Error while saving:"+e.getMessage(),
							Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(getActivity(),"Save successFull:",Toast.LENGTH_SHORT).show();
				
				listener.bindScann();
			}
		});
	}
	
	private void setOnCollectionSelected(Spinner spinner, final SimpleCursorAdapter adapter) {
		final EditText collectionCreate = (EditText) view.findViewById(R.id.create_collection);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapter_, View arg1,
					int position, long arg3) {
				String createCollectionLabel = getResources().getString(R.string.create_collection);
				Cursor cursor = (Cursor) adapter.getItem(position);
				Collection collection = CollectionDalWrapper
						.getObjectFromDB(new CollectionCursor(cursor), 0);
				if(createCollectionLabel.equals(collection.getname())) {
					collectionCreate.setVisibility(View.VISIBLE);
				} else {
					collectionCreate.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}
	
	private void openCalendarAction(EditText v) {
		KLog.d(getActivity(), TAG, "openCalendar");
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				KLog.d(getActivity(), TAG, "dateEditText:onClick");
				openDatePickerDialog();
			}
		});
	}
	
	private void updateEditTextDate(String myDate) {
		EditText parutionDate = (EditText) view.findViewById(R.id.tome_parution_date);
		parutionDate.setText(myDate);
	}
	
	private void openDatePickerDialog() {
		final OnDateSetListener callBack = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//				updateEditTextDate(DateGetter.getStringFromDate(calendar.getTime()));
				updateEditTextDate(String.valueOf(year));
			}
		};
		DatePickerDialog dialog = new DatePickerDialog(
				getActivity(), 
//				theme, 
				callBack, 
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH+1), 
				calendar.get(Calendar.DAY_OF_MONTH)
				);
		dialog.show();
	}
	
}