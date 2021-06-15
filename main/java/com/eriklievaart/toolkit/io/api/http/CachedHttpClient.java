package com.eriklievaart.toolkit.io.api.http;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.function.Function;

import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class CachedHttpClient implements HttpClient {

	private final Function<String, InputStream> fetch;
	private final HttpClient delegate;

	public CachedHttpClient(File root) {
		this(root, new SimpleHttpClient());
	}

	public CachedHttpClient(File root, HttpClient delegate) {
		root.mkdirs();
		CheckFile.isDirectory(root);

		this.delegate = delegate;
		fetch = new HttpDirectoryCache(root, delegate);
	}

	public CachedHttpClient(Function<String, InputStream> cache) {
		this.delegate = new SimpleHttpClient();
		this.fetch = cache;
	}

	@Override
	public HttpURLConnection getHttpUrlConnection(String url) {
		return delegate.getHttpUrlConnection(url);
	}

	@Override
	public String getString(String url) {
		Check.notBlank(url);
		return StreamTool.toString(fetch.apply(url));
	}

	@Override
	public List<String> getLines(String url) {
		Check.notBlank(url);
		return StreamTool.readLines(fetch.apply(url));
	}

	@Override
	public void defaultHeaderIfAbsent(String name, String value) {
		delegate.defaultHeaderIfAbsent(name, value);
	}

	@Override
	public InputStream getInputStream(String url) {
		Check.notBlank(url);
		return fetch.apply(url);
	}

	@Override
	public InputStream getInputStream(HttpCall call) {
		return delegate.getInputStream(call);
	}

	@Override
	public CachedHttpClient socks5(String ip, int port) {
		delegate.socks5(ip, port);
		return this;
	}
}