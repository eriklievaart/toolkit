package com.eriklievaart.toolkit.io.api.http;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class SimpleHttpClient implements HttpClient {
	private LogTemplate log = new LogTemplate(getClass());

	public String getString(String url) {
		return StreamTool.toString(getInputStream(url));
	}

	public List<String> getLines(String url) {
		return StreamTool.readLines(getInputStream(url));
	}

	public InputStream getInputStream(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");

			int responseCode = con.getResponseCode();
			log.debug("GET URL : " + url + " status " + responseCode);

			return con.getInputStream();

		} catch (Exception e) {
			throw new RuntimeIOException("Unable to read URL %", e, url);
		}
	}
}
