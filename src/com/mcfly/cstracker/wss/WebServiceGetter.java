package com.mcfly.cstracker.wss;

public class WebServiceGetter {
	
	private static final String BASE_URL = "http://192.168.10.4:8180/fphtouch";
	
	private final static String URL = "http://xisbn.worldcat.org/webservices/xid/isbn/";
	private final static String URL_METHOD = "?method=getMetadata&format=json&fl=*";
	

	public static String[] getIsbn(String isbn) {
		String[] result = {String.format("%s%s%s", 
				URL,
				isbn,
				URL_METHOD)
				};
		return result;
	}
}
