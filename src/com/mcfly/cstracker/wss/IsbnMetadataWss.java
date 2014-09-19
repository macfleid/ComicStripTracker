package com.mcfly.cstracker.wss;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.util.Log;
/**
 * 
 * @author mcfly
 * @deprecated
 * 
 * old version for tests 
 */
public class IsbnMetadataWss extends AsyncTask<String, Integer, HashMap<String, String>>
{
	private final static String url = "http://xisbn.worldcat.org/webservices/xid/isbn/2-8036-1960-1?method=getMetadata&format=json&fl=*";
	
	private final static String URL = "http://xisbn.worldcat.org/webservices/xid/isbn/";
	private final static String URL_METHOD = "?method=getMetadata&format=json&fl=*";
	
	
	@Override
	protected HashMap<String, String> doInBackground(String... params) {
		String isbnNumber = params[0];
		String result = readJSONFeed(URL+isbnNumber+URL_METHOD);
		Log.d("IsbnMetadataWss", "result "+result);
		try {
			HashMap<String, String> isbnMetadata = parseJSONFeed(result);
			return isbnMetadata;
		} catch (JSONException e) {
			Log.e("IsbnMetadataWss", "error while reaching isbn information",e);
		}
		return null;
	}
	
	///---------
	
	private String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("IsbnMetadataWss", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("IsbnMetadataWss", e.getLocalizedMessage());
        }        
        return stringBuilder.toString();
    }
	
	
	private HashMap<String, String> parseJSONFeed(String resultFeed) throws JSONException {
		Log.d("IsbnMetadataWss", "...parseJSONFeed ");		
		HashMap<String, String> result = new HashMap<String, String>();
		
		JSONObject jo = new JSONObject(resultFeed);
		@SuppressWarnings("unchecked")
		Iterator<String> it = jo.keys();
		while(it.hasNext()) {
			String key = it.next();
			Log.d("IsbnMetadataWss", "#key: "+key.toString());
			
			Log.d("IsbnMetadataWss", "	key: "+key);
			Log.d("IsbnMetadataWss", "	val: "+jo.getString(key));
			if (jo.get(key) instanceof JSONArray) {
				Log.d("IsbnMetadataWss", "	array detected: ");
				JSONArray jsonArray = jo.getJSONArray(key);
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jo_=(JSONObject)jsonArray.get(i);
			        @SuppressWarnings("unchecked")
					Iterator<String> it_ = jo_.keys();
			           while(it_.hasNext()) {
			        	   String key_ = it_.next();
			        	   Log.d("IsbnMetadataWss", "---key of array "+key_);
			        	   result.put(key_, jo_.getString(key_));
			        	   jo_.getString(key_);
			           }
			    }
			} else {
				result.put(key, jo.getString(key));
			}
		}
		return result;
	}

	
	//-----
	
}
