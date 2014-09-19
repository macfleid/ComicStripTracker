package com.mcfly.cstracker.sync.model;

import java.io.Serializable;
import java.util.List;

import com.mcfly.cstracker.scann.action._AbstractSyncModel;

public class IsbnModel extends _AbstractSyncModel implements Serializable {
	
	private List<IsbnProperties> list;
	private String stat;
	
	public List<IsbnProperties> getList() {
		return list;
	}
	public void setList(List<IsbnProperties> list) {
		this.list = list;
	}
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	
}
