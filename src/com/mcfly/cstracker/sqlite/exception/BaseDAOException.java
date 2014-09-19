package com.mcfly.cstracker.sqlite.exception;

public class BaseDAOException extends Exception {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseDAOException(Throwable t) {
		super(t);
	}
	
	public BaseDAOException(String t) {
		super(t);
	}

}
