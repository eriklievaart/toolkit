package com.eriklievaart.toolkit.io.api.http;

import java.util.Collections;
import java.util.Map;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class HttpCall {

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
		return UrlParameterEncoder.encodeToBytes(parameters);
	}
}