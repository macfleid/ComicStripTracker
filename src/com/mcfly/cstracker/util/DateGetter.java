package com.mcfly.cstracker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class DateGetter 
{
	private static DateGetter instance;
	
	public static DateGetter getInstance() {
		if(instance==null) {
			instance = new DateGetter();
		}
		return instance;
	}
	
	private DateGetter() {
		
	}

	public Date getDateFromString(String value) {
		Date date = null;
		if(value!=null && !value.isEmpty()) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(value);
			} catch (ParseException e) {
				Log.e("DateGetter", "Error while getting date",e);
			}
		}
		return date;
	}
	
	public static String getStringFromDate(Date date) {
		String result="";
		if(date!=null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			result = format.format(date);
		}
		return result;
	}
	
	
	/**
	 * For CLin Form 
	 * @param date
	 * @return
	 */
	public static String getStringFromDate_(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String t = format.format(date);
		return t;
	}
	
	/**
	 * For CLin Form
	 * @param value
	 * @return
	 */
	public static Date getCFDateFromString_(String value) {
		Date date = null;
		if(value!=null && !value.isEmpty()) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				date = sdf.parse(value);
			} catch (ParseException e) {
				Log.e("DateGetter", "Error while getting date",e);
			}
		}
		return date;
	}
	
}
