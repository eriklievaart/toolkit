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
			HttpURLConnection con = connect(new URL(call.getUrl()));

			con.setRequestMethod(call.getMethod());
			headers.forEach((name, value) -> con.setRequestProperty(name, value));

			byte[] bytes = call.getBodyBytes();
			if (bytes.length > 0) {
				con.setDoOutput(true);
				con.getOutputStream().write(bytes);
			}
			int responseCode = con.getResponseCode();
			boolean proxied = proxyReference.get() != null;
			log.debug("$ URL % proxy $ status $", call.getMethod(), call.getUrl(), proxied, responseCode);

			return con.getInputStream();

		} catch (Exception e) {
			throw new RuntimeIOException("Unable to read URL %", e, call.getUrl());
		}
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
