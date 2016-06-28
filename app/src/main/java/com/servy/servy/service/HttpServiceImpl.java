package com.servy.servy.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpServiceImpl implements HttpService{
	
 
    private static int timeoutConnection = 3000;  
    private static int timeoutSocket = 5000;  
    
  
 
 /*
  * get InputStream from the specify URL
  * */
    private InputStream get(String urlStr) {
		HttpURLConnection urlConnection=null;

		try {
			//get connection
			URL url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-length", "0");
			urlConnection.setUseCaches(false);
			urlConnection.setAllowUserInteraction(false);
			urlConnection.setConnectTimeout(timeoutConnection);
			urlConnection.setReadTimeout(timeoutSocket);
			urlConnection.connect();

			//check if connection success if success return input stream
			int status = urlConnection.getResponseCode();
			switch (status) {
				case 200:
				case 201:
					return urlConnection.getInputStream();
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			if(urlConnection != null){
				urlConnection.disconnect();
			}
		}

		return null;

	}


	public InputStream post(String urlStr,String param) {
		HttpURLConnection urlConnection=null;

		try {
			//get connection
			URL url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			//urlConnection.setRequestProperty("Content-length", "0");
			urlConnection.setUseCaches(false);
			urlConnection.setAllowUserInteraction(false);
			urlConnection.setConnectTimeout(timeoutConnection);
			urlConnection.setReadTimeout(timeoutSocket);
			urlConnection.setDoOutput(true);
			//urlConnection.connect();
			OutputStream out=urlConnection.getOutputStream();
			out.write(param.getBytes());
			out.flush();
			out.close();

			//check if connection success if success return input stream
			int status = urlConnection.getResponseCode();
			switch (status) {
				case 200:
				case 201:
					return urlConnection.getInputStream();
			}
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			if(urlConnection != null){
				urlConnection.disconnect();
			}
		}

		return null;

	}

    
    //Check whether device connect to the Internet
    public boolean isInternetAvailable(Context context){
    	ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        if(connManager.getActiveNetworkInfo() != null) {  
            return connManager.getActiveNetworkInfo().isAvailable();  
        }  
        return false;  
    }

    /*
     * Transfer the input stream into JSON object
     * */
	@Override
	public JSONObject getJSONObject(String url,String param) {

    	
        try {
			//InputStream is=new URL(url).openStream();
        	BufferedReader br = new BufferedReader(new InputStreamReader(post(url,param),"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
    		while ((line = br.readLine()) != null) {
    		    sb.append(line+"\n");
    		}
    		br.close();
    		String json  = sb.toString().trim();
    		return new JSONObject(json);
    	} catch (JSONException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}

	@Override
	public Bitmap getBitmap(String url) {
		return BitmapFactory.decodeStream(get(url));
	}
 
}
