package com.example.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
	private String url;
	private String cookie;
	private String post;
	private String charset;

	public HttpRequest(String u) {
		url = u;
	}

	public HttpRequest(String u, String p) {
		url = u;
		post = p;
	}

	public void setCookie(String c) {
		cookie = c;
	}

	public void setCharset(String c) {
		charset = c;
	}

	public Response query() {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(8000);
			conn.setReadTimeout(8000);
			if (null == post) {
				conn.setRequestMethod("GET");
			} else {
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				byte[] postByte = post.getBytes();
				conn.getOutputStream().write(postByte);
			}
			if (null != cookie)
				conn.setRequestProperty("Cookie", cookie);

			InputStream responseStream = conn.getInputStream();
			InputStreamReader responseReader;
			if (null == charset) {
				responseReader = new InputStreamReader(responseStream, "utf-8");
			} else {
				responseReader = new InputStreamReader(responseStream, charset);
			}

			BufferedReader reader = new BufferedReader(responseReader);
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			conn.disconnect();
			return new Response(true, response.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(false, e.toString());
		} finally {
			if (null != conn)
				conn.disconnect();
		}
	}
}
