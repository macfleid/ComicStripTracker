package com.mcfly.cstracker.wss;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask.Status;
import android.util.Log;

import com.mcfly.cstracker.scann.action._AbstractSyncModel;
import com.mcfly.cstracker.sync.model.IsbnModel;
import com.mcfly.cstracker.wss.client.ISyncListener;
import com.mcfly.cstracker.wss.client.WsClientImpl;

public class IsbnWss extends _AbstractSyncWebService<IsbnModel> {
	
	private final static String TAG = IsbnWss.class.getName();

	public IsbnWss(Context c, ISyncListener listener) {
		super(c, listener);
	}
	
	public void getIsbnInfos(String isbn) {
		Log.d(TAG, "getIsbnInfos");
		String[] url = WebServiceGetter.getIsbn(isbn);
		
		if(wsClient!=null && wsClient.getStatus()!=Status.FINISHED) {
			wsClient.cancel(true);
		}
		wsClient = new WsClientImpl(this.context,this);
		wsClient.execute(url);
	}
	

	@Override
	protected void processResult(IsbnModel result) {
		Log.d(TAG, "processResult");
		if(result != null) {
			listener.notifyEnd(result);
		} else {
			listener.notifyError();
		}
	}

	@Override
	protected void setClassModel() {
		this.myCLass = IsbnModel.class;
	}

}
