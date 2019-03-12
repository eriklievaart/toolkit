package com.eriklievaart.toolkit.io.api.http;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;

import com.eriklievaart.toolkit.io.api.CheckFile;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class CachedHttpClient implements HttpClient {

	private Function<String, InputStream> fetch;
	private HttpClient delegate = new SimpleHttpClient();

	public CachedHttpClient(File root) {
		root.mkdirs();
		CheckFile.isDirectory(root);

		fetch = new HttpDirectoryCache(root, delegate);
	}

	public CachedHttpClient(Function<String, InputStream> cache) {
		this.fetch = cache;
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
	public InputStream getInputStream(String url) {
		Check.notBlank(url);
		return fetch.apply(url);
	}

	@Override
	public void setHeader(String name, String value) {
		delegate.setHeader(name, value);
	}
}
