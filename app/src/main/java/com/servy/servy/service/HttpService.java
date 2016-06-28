package com.servy.servy.service;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.InputStream;

public interface HttpService {
	boolean isInternetAvailable(Context context);
	JSONObject getJSONObject(String urlStr, String param);
	Bitmap getBitmap(String urlStr);
	InputStream post(String urlStr, String param);
}
