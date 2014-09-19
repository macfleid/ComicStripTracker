package com.mcfly.cstracker.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mcfly.cstracker.R;
import com.mcfly.cstracker.activities.IMainActivityListener;
import com.mcfly.cstracker.scann.action.IDataSyncFinished;
import com.mcfly.cstracker.scann.action.IsbnSyncAction;
import com.mcfly.cstracker.sync.model.IsbnModel;

public class ScannFragment  extends Fragment implements IDataSyncFinished {
	
	private final static String TAG = ScannFragment.class.getName();
	
	private View view;
	private IMainActivityListener listener;
	
	private ProgressDialog waitingDialog;
	
	private IsbnSyncAction isbnSyncAction;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.listener = (IMainActivityListener) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_scann,
				container, false);
		Button button = (Button) view.findViewById(R.id.buttonScann);
		Button buttonManual = (Button) view.findViewById(R.id.button_manual);
		Button buttonScannAndShare = (Button) view.findViewById(R.id.buttonScannForShare);
		
		setScannAction(button);
		setManualAction(buttonManual);
		setScannAndShareAction(buttonScannAndShare);
		
		return view;
	}
	
	
	private void setScannAction(Button button) {
		final IDataSyncFinished l = this;
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN"); 
				intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "SCAN_MODE");
                startActivityForResult(intent, 0);
			}
		});
	}
	
	private void setManualAction(Button button) {
		final IDataSyncFinished l = this;
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.bindIsbnResult(new IsbnModel());
			}
		});
	}
	
	private void setScannAndShareAction(Button button) {
		final IDataSyncFinished l = this;
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Not yet implemented", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (requestCode == 0) {
             if (resultCode == Activity.RESULT_OK) {
            	 String contents = data.getStringExtra("SCAN_RESULT");
            	 String format = data.getStringExtra("SCAN_RESULT_FORMAT");

            	 Log.d(TAG,"contents:"+contents);
            	 Log.d(TAG,"format:"+format);
            	 
            	 showWaiting();
            	 isbnSyncAction = new IsbnSyncAction(getActivity(), this, contents);
            	 isbnSyncAction.startSync();
                                          
             } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d(TAG,"Scan unsuccessful");
             }
        }
	}
	
	private void showWaiting() {
		waitingDialog = new ProgressDialog(getActivity());
		waitingDialog.setTitle("Processing...");
		waitingDialog.setMessage("Please wait.");
		waitingDialog.setCancelable(false);
		waitingDialog.setIndeterminate(true);
		waitingDialog.show();
	}
	
	private void dissmissWaiting() {
		if(waitingDialog != null) {
			waitingDialog.dismiss();
		}
	}

	//---------------------------------------------------------------
	//-- IDataSyncFinished
	//---------------------------------------------------------------
	
	@Override
	public void notifySyncFinished() {
		dissmissWaiting();
		listener.bindIsbnResult(isbnSyncAction.getResult());
	}

	@Override
	public void notifySyncError() {
		dissmissWaiting();
		Toast.makeText(getActivity(), "Error while getting isbn informations", Toast.LENGTH_SHORT).show();
	}

}
