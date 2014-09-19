package com.mcfly.cstracker.scann.action;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.mcfly.cstracker.sync.model.IsbnModel;
import com.mcfly.cstracker.wss.IsbnWss;
import com.mcfly.cstracker.wss._AbstractSyncListener;


public class IsbnSyncAction extends _AbstractSyncListener {
	
	private final static String TAG = IsbnSyncAction.class.getName();

	private IsbnWss wss;
	private String isbnString;
	private IDataSyncFinished listener;
	
	private IsbnModel result;
	
	public IsbnSyncAction(Context c, IDataSyncFinished listener, String isbn) {
		super(c);
		Log.d(TAG, "[IsbnSyncAction]");
		wss = new IsbnWss(c, this);
		this.isbnString = isbn;
		this.listener = listener;
	}

	@Override
	protected boolean handleNotifyEnd(_AbstractSyncModel IsbnModel) {
		Log.d(TAG, "[handleNotifyEnd]");
		result = (IsbnModel) IsbnModel;
		listener.notifySyncFinished();
		return true;
	}

	@Override
	protected void handleNotifyError() {
		Log.d(TAG, "[handleNotifyError]");
		listener.notifySyncError();
	}

	@Override
	protected void startSync_() {
		Log.d(TAG, "[startSync_]");
		wss.getIsbnInfos(isbnString);
	}

	public IsbnModel getResult() {
		return result;
	}

}
