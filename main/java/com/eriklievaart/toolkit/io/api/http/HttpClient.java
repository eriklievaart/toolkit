package com.eriklievaart.toolkit.io.api.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.check.Check;

public interface HttpClient {

	public String getString(String url);

	public List<String> getLines(String url);

	public InputStream getInputStream(String url);

	public InputStream getInputStream(HttpCall call);

	public void setHeader(String name, String value);

	public HttpClient socks5(String ip, int port);

	public default void download(String url, File file) {
		Check.notBlank(url);
		Check.notNull(file);
		file.getParentFile().mkdirs();

		try (InputStream is = getInputStream(url)) {
			StreamTool.copyStream(is, new FileOutputStream(file));

		} catch (IOException e) {
			new RuntimeIOException(e);
		}
	}
}