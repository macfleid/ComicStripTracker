package com.mcfly.cstracker.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import android.content.Context;

public class KLog  {
	
	private static final String TAG = KLog.class.getName();
	
	static final boolean LOG = true;
	static final boolean LOG_FULL = false;
	static final boolean LOG_ERROR = false;
	
	private final static String ErrorLogPath = "LogError.txt";
	private final static String FullLogPath = "LogFull.txt";
	
	//----------------

    public static void i(String tag, String string) {
        if (LOG) android.util.Log.i(tag, string);
    }
    
    @SuppressWarnings("unused")
	public static void e(Context c, String tag, String string) {
        if (LOG) {
        	android.util.Log.e(tag, string);
        	String res = "";
        	if(LOG_FULL || LOG_ERROR) {
        		res = formatString(tag, string);
        	}
        	if(LOG_ERROR) {
	        	writeToFile(c, ErrorLogPath, res);
        	}
        	if(LOG_FULL) {
	        	writeToFile(c, FullLogPath, res);
        	}
        }
    }
    
    @SuppressWarnings("unused")
	public static void e(Context c, String tag, String string, Throwable t) {
        if (LOG) {
        	android.util.Log.e(tag, string, t);
        	String res = "";
        	if(LOG_FULL || LOG_ERROR) {
        		res = formatString(tag, string, t);
        	}
        	if(LOG_ERROR) {
	        	writeToFile(c, ErrorLogPath, res);
        	}
        	if(LOG_FULL) {
	        	writeToFile(c, FullLogPath, res);
        	}
        }
    }
    
    public static void d(Context c, String tag, String string) {
        if (LOG) { 
        	android.util.Log.d(tag, string);
        	if(LOG_FULL) {
        		String res = "";
        		res = formatString(tag, string);
	        	writeToFile(c, FullLogPath, res);
        	}
        }
        
    }
    
    public static void v(String tag, String string) {
        if (LOG) android.util.Log.v(tag, string);
    }
    
    public static void w(Context c, String tag, String string) {
        if (LOG) {
        	android.util.Log.w(tag, string);
        	if(LOG_FULL) {
        		String res = "";
        		res = formatString(tag, string);
	        	writeToFile(c, FullLogPath, res);
        	}
        }
    }
    
    

    
    ///---------------
    
    private static void writeToFile(Context c, String path, String value) {
    	OutputStream os = null;
    	try {
    		os = c.openFileOutput(path, Context.MODE_PRIVATE | Context.MODE_APPEND);
    		BufferedOutputStream bos = new BufferedOutputStream(os, value.length());
    		bos.write(value.getBytes());
			bos.close();
		} catch (IOException e) {
			android.util.Log.e(TAG, "Error while writing logFile:"+path, e);
		} finally {
			
		}
    }
    
    private static void writeToFile(Context c, String path, String[] value) {
    	OutputStream os = null;
    	try {
    		os = c.openFileOutput(path, Context.MODE_PRIVATE);
    		BufferedOutputStream bos = new BufferedOutputStream(os);
    		for(String el : value ) {
    			bos.write(el.getBytes());
    		}
    		bos.close();
		} catch (IOException e) {
			android.util.Log.e(TAG, "Error while writing logFile:"+path, e);
		} finally {
			
		}
    }
    
    
    private static String formatString(String tag, String value) {
    	String result = "";
    	String date = DateGetter.getStringFromDate(new Date());
    	result = String.format("#%s [%s] : [%s]\n", date, tag, value);
    	
    	return result;
    }
    	
    
    private static String formatString(String tag, String value, Throwable T) {
    	String result = "";
    	String throwableString = "";
    	getStringFromThrowable(T,throwableString);
    	result = String.format("%s \n -----------> %s", formatString(tag, value), throwableString);
    	return result;
    }
    
    private static String getStringFromThrowable(Throwable T, String res) {
    	if(T.getCause()!=null) {
    		getStringFromThrowable(T.getCause(), res);
    	} else {
    		if(T.getMessage() != null ) {
    			res += T.getMessage();
    		}
    	}
    	return res;
    }

}
