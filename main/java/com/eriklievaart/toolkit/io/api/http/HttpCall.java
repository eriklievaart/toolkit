package com.eriklievaart.toolkit.io.api.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import com.eriklievaart.toolkit.lang.api.FormattedException;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class HttpCall {

	private static final String UTF = "UTF-8";
	private String url;
	private String method = "GET";
	private Map<String, String> parameters = NewCollection.map();

	public HttpCall(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String value) {
		method = value;
	}

	public void bodyParameter(String key, String value) {
		parameters.put(key, value);
	}

	public Map<String, String> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	public byte[] getBodyBytes() {
		if (parameters.isEmpty()) {
			return new byte[0];
		}
		try {
			return getParameterString().getBytes(UTF);
		} catch (UnsupportedEncodingException e) {
			throw new FormattedException("encoding problem", e);
		}
	}

	private String getParameterString() throws UnsupportedEncodingException {
		Iterator<String> iterator = parameters.keySet().iterator();

		StringBuilder data = new StringBuilder();
		appendParameter(data, iterator.next());
		while (iterator.hasNext()) {
			data.append('&');
			appendParameter(data, iterator.next());
		}
		return data.toString();
	}

	private void appendParameter(StringBuilder data, String key) throws UnsupportedEncodingException {
		data.append(URLEncoder.encode(key, UTF));
		data.append('=');
		data.append(URLEncoder.encode(String.valueOf(parameters.get(key)), UTF));
	}
}