package com.mcfly.cstracker.wss.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author mcfly
 *
 */
public class WsClientImpl extends AsyncTask<String, Integer, String> implements IWsClient {

	private static final String TAG = WsClientImpl.class.getName();
	protected Context context;
	private IWsResult listener;
	
	public WsClientImpl(Context c, IWsResult listener) {
		this.context = c;
		this.listener = listener;
	}
	
	@Override
	protected String doInBackground(String... params) {
		Log.d(TAG, "... call doInBackground()");
		String result = null;
		String url = params[0];
		String[] param = new String[params.length-1];
		int i2=0;
		for(int i=1; i<params.length; i++) {
			param[i2]=params[i];
			i2++;
		}
		result = callWs(url, param );
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		listener.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
	
	//------------

	@Override
	public String callWs(String url, String[] params) {
		Log.d(TAG, "... call callWs()");
		StringBuilder stringBuilder = new StringBuilder();
		HttpParams httpParams = new BasicHttpParams();
		int timeOutConnection = 3000;
		int timeOutSocket = 5000;
		HttpConnectionParams.setConnectionTimeout(httpParams, timeOutConnection);
		HttpConnectionParams.setSoTimeout(httpParams, timeOutSocket);
		HttpClient client = new DefaultHttpClient(httpParams);
		String completeUrl = null;
		HttpGet httpGet = null;
		try {
			completeUrl = url;
			for(String param : params ) {
				completeUrl += param;
			}
			httpGet = new HttpGet(completeUrl);
			Log.d(TAG, "Request :"+completeUrl);
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			Log.d(TAG, "   ...request status:"+statusLine);
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
				is.close();
				Log.d(TAG, "Result  :"+stringBuilder.toString());
			} else {
				listener.signalError();
			}
		} catch (ClientProtocolException e) {
			Log.e(TAG, "Exception occured when callWs", e);
			listener.signalError();
		} catch (IOException e) {
			Log.e(TAG, "Exception occured when callWs", e); 
			listener.signalError();
		} finally {
			
		}
		return stringBuilder.toString();
	}
	
	

}
