package com.eriklievaart.toolkit.io.api.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.function.Function;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.io.api.UrlTool;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class HttpDirectoryCache implements Function<String, InputStream> {
	private LogTemplate log = new LogTemplate(getClass());

	private File root;
	private HttpClient client;

	public HttpDirectoryCache(File root, HttpClient client) {
		Check.notNull(root, client);
		this.root = root;
		this.client = client;
	}

	@Override
	public InputStream apply(String url) {
		String base = encode(UrlTool.getPath(url));
		File cached = new File(root, base);
		try {
			if (!cached.exists()) {
				log.trace("cache miss for % -> $", url, cached);
				InputStream input = client.getInputStream(url);
				StreamTool.copyStream(input, new FileOutputStream(cached));
			}
			return new FileInputStream(cached);

		} catch (FileNotFoundException e) {
			throw new RuntimeIOException("Unable process cached HTTP request % -> $", e, url, cached);
		}
	}

	static String encode(String string) {
		return string.replaceFirst(".*:/*+", "").replace('/', '_');
	}
}
