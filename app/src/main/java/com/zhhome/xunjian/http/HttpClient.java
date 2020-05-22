package com.zhhome.xunjian.http;


import com.loopj.android.http.AsyncHttpClient;

public class HttpClient extends AsyncHttpClient {
	private static HttpClient client;

	public static HttpClient getInstance() {
		if(client == null) {
			client = new HttpClient();
		}
		return client;
	}
	private HttpClient() {}


}
