package com.eriklievaart.toolkit.io.api.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.eriklievaart.toolkit.lang.api.FormattedException;

public class UrlParameterEncoder {
	public static final String UTF = "UTF-8";

	public static byte[] encodeToBytes(Map<String, String> parameters) {
		try {
			return encodeToString(parameters).getBytes(UrlParameterEncoder.UTF);
		} catch (UnsupportedEncodingException e) {
			throw new FormattedException("encoding problem", e);
		}
	}

	public static String encodeToString(Map<String, String> parameters) {
		if (parameters.isEmpty()) {
			return "";
		}
		try {
			Iterator<Entry<String, String>> iterator = parameters.entrySet().iterator();

			StringBuilder data = new StringBuilder();
			appendParameter(data, iterator.next());
			while (iterator.hasNext()) {
				data.append('&');
				appendParameter(data, iterator.next());
			}
			return data.toString();
		} catch (UnsupportedEncodingException e) {
			throw new FormattedException("encoding problem", e);
		}
	}

	private static void appendParameter(StringBuilder data, Entry<String, ?> entry)
			throws UnsupportedEncodingException {
		data.append(URLEncoder.encode(entry.getKey(), UTF));
		data.append('=');
		data.append(URLEncoder.encode(String.valueOf(entry.getValue()), UTF));
	}
}
