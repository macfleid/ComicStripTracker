package com.mcfly.cstracker.wss;

import com.mcfly.cstracker.wss.client.IWsResult;
import com.mcfly.cstracker.wss.client.WsClientImpl;

import android.content.Context;
import android.provider.Settings.Secure;


public abstract class _AbstractWebService implements IWsResult {
	
	protected Context context;
	protected WsClientImpl wsClient;
	
	
	public _AbstractWebService(Context c) {
		this.context = c;
	}
	
	protected void startWs(String[] params) {
		wsClient = new WsClientImpl(this.context,this);
		wsClient.execute(params);
	}
	
	protected String getDeviceId() {
		String id = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		return id;
	}
	
}
