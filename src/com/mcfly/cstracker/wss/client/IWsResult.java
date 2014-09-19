package com.mcfly.cstracker.wss.client;

public interface IWsResult {
	
	public void onPostExecute(String result);
	
	public void signalError();
	
}
