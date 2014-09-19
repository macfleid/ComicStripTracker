package com.mcfly.cstracker.scann.action;

/**
 * 
 * interface to implements on contexts needing a sync before
 * @author mcfly
 */
public interface IDataSyncFinished {
	
	/**
	 * 
	 * methods to notify the activity that the sync has finished successfully
	 */
	public void notifySyncFinished();
	
	/**
	 * 
	 * methods to notify the activity that the sync has finished with some errors
	 * activity should not continue normally after catch this
	 */
	public void notifySyncError();
	

}
