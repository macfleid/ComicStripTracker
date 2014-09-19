package com.mcfly.cstracker.activities;

import com.mcfly.cstracker.sqlite.dal.Collection;
import com.mcfly.cstracker.sync.model.IsbnModel;

public interface IMainActivityListener {

	public void bindIsbnResult(IsbnModel model);
	
	public void bindScann();
	
	public void bindLibrary();
	
	public void bindBackLibrary();
	
	public void bindWaitUI();
	
	public void bindLibraryCollection(Collection collection);
}
