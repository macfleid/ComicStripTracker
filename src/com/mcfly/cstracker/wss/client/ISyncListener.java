package com.mcfly.cstracker.wss.client;

import java.util.List;

import com.mcfly.cstracker.scann.action._AbstractSyncModel;

public interface ISyncListener {

	/**
	 * call to notify the end of sync
	 */
	public void notifyEnd(_AbstractSyncModel IsbnModel);
	
	/**
	 * errors on web service calls are transmitted here
	 */
	public void notifyError();
	
	/**
	 * start the sync process
	 */
	public void startSync();
	
	
}
