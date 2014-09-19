package com.mcfly.cstracker.wss;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcfly.cstracker.scann.action._AbstractSyncModel;
import com.mcfly.cstracker.wss.client.ISyncListener;

import android.content.Context;

public abstract class _AbstractSyncWebService<T extends _AbstractSyncModel> extends _AbstractWebService {

	private final static String TAG = _AbstractSyncWebService.class.getName();
	protected ISyncListener listener;
	protected Class<T> myCLass;
	
	public _AbstractSyncWebService(Context c, ISyncListener listener) {
		super(c);
		this.listener = listener;
		setClassModel();
	}
	
	@Override
	public void signalError() {
		listener.notifyError();
	}
	
	@Override
	public void onPostExecute(String result) {
		//-- JSONObject to our model
		Gson gson = new GsonBuilder().create();
		T result_ = gson.fromJson(result, myCLass);
		
		processResult(result_);
	}
	
	protected abstract void processResult(T result);

	protected abstract void setClassModel();
	

}
