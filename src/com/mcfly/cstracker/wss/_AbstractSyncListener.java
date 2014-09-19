package com.mcfly.cstracker.wss;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.mcfly.cstracker.scann.action._AbstractSyncModel;
import com.mcfly.cstracker.sqlite.DbManager;
import com.mcfly.cstracker.wss.client.ISyncListener;

public abstract class _AbstractSyncListener implements ISyncListener {
	
	private final static String TAG = _AbstractSyncListener.class.getName();

	protected Context context;
	private SQLiteDatabase database;
	private boolean transactionError;
	
	//-------------------------------------------------
	
	public _AbstractSyncListener(Context c) {
		this.context = c;
	}

	@Override
	public void notifyEnd(_AbstractSyncModel IsbnModel) {
		handleNotifyEnd(IsbnModel);
	}

	@Override
	public void notifyError() {
		Log.d( TAG,"... notifyError");
		handleNotifyError();		
	}
	
	@Override
	public void startSync() {
		Log.d( TAG,"... call startSync");
		// Asynctask for synchronisation
		HandlerThread handlerBgThread = new HandlerThread("SYNC");
		handlerBgThread.start();
		Handler handler = new Handler(handlerBgThread.getLooper());
		handler.post(getRunnable());

	}
	
	private Runnable getRunnable() {
		Runnable result = new Runnable() {
			@Override
			public void run() {
				startSync_();
			}
		};
		return result;
	}
	
	
	protected abstract boolean handleNotifyEnd(_AbstractSyncModel IsbnModel);
	
	protected abstract void handleNotifyError();
	
	protected abstract void startSync_();
	
	///-----------
	
}
