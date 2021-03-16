package com.eriklievaart.toolkit.io.api.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class SimpleHttpClient implements HttpClient {
	private LogTemplate log = new LogTemplate(getClass());

	private Map<String, String> headers = NewCollection.concurrentMap();
	private AtomicReference<Proxy> proxyReference = new AtomicReference<>();

	{
		headers.put("Accept", "*/*");
		headers.put("User-Agent", "Googlebot/2.1");
	}

	@Override
	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

	@Override
	public SimpleHttpClient socks5(String ip, int port) {
		proxyReference.set(new Proxy(Type.SOCKS, new InetSocketAddress(ip, port)));
		return this;
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
			return getHttpUrlConnection(call).getInputStream();
		} catch (IOException e) {
			throw new RuntimeIOException("Unable to read URL %", e, call.getUrl());
		}
	}

	@Override
	public HttpURLConnection getHttpUrlConnection(String url) {
		return getHttpUrlConnection(new HttpCall(url));
	}

	public HttpURLConnection getHttpUrlConnection(HttpCall call) {
		try {
			HttpURLConnection connection = connect(new URL(call.getUrl()));

			connection.setRequestMethod(call.getMethod());
			setHeaders(connection);

			byte[] bytes = call.getBodyBytes();
			if (bytes.length > 0) {
				connection.setDoOutput(true);
				connection.getOutputStream().write(bytes);
			}
			boolean proxied = proxyReference.get() != null;
			logResponseResult(call, connection, proxied);
			return connection;

		} catch (Exception e) {
			throw new RuntimeIOException("Unable to read URL %", e, call.getUrl());
		}
	}

	private void logResponseResult(HttpCall call, HttpURLConnection con, boolean proxied) throws IOException {
		log.debug("$ URL % proxy $ status $", call.getMethod(), call.getUrl(), proxied, con.getResponseCode());
		if (log.isTraceEnabled()) {
			con.getHeaderFields().forEach((k, v) -> log.trace("response header % = %", k, v));
		}
	}

	private void setHeaders(HttpURLConnection con) {
		headers.forEach((name, value) -> {
			log.trace("header: $: $", name, value);
			con.setRequestProperty(name, value);
		});
	}

	private HttpURLConnection connect(URL url) throws IOException {
		Proxy proxy = proxyReference.get();
		if (proxy == null) {
			return (HttpURLConnection) url.openConnection();
		} else {
			return (HttpURLConnection) url.openConnection(proxy);
		}
	}
}