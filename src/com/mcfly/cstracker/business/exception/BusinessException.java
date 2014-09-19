package com.mcfly.cstracker.business.exception;

public class BusinessException extends Exception {

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	public BusinessException(Throwable t) {
		super(t);
	}
	
	public BusinessException(String t) {
		super(t);
	}
	
	public BusinessException(String t, Throwable throwable) {
		super(t,throwable);
	}

}
