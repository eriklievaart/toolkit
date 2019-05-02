package com.eriklievaart.toolkit.io.api.http;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class SimpleHttpClient implements HttpClient {
	private LogTemplate log = new LogTemplate(getClass());

	private Map<String, String> headers = NewCollection.map();

	{
		headers.put("Accept", "*/*");
	}

	@Override
	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

	@Override
	public String getString(String url) {
		return StreamTool.toString(getInputStream(url));
	}

	@Override
	public List<String> getLines(String url) {
		return StreamTool.readLines(getInputStream(url));
	}

	@Override
	public InputStream getInputStream(String url) {
		return getInputStream(new HttpCall(url));
	}

	@Override
	public InputStream getInputStream(HttpCall call) {
		try {
			URL obj = new URL(call.getUrl());
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod(call.getMethod());
			headers.forEach((name, value) -> con.setRequestProperty(name, value));

			byte[] bytes = call.getBodyBytes();
			if (bytes.length > 0) {
				con.setDoOutput(true);
				con.getOutputStream().write(bytes);
			}
			int responseCode = con.getResponseCode();
			log.debug("$ URL % status $", call.getMethod(), call.getUrl(), responseCode);

			return con.getInputStream();

		} catch (Exception e) {
			throw new RuntimeIOException("Unable to read URL %", e, call.getUrl());
		}
	}
}
