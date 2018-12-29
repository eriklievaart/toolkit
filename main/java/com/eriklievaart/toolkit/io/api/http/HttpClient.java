package com.eriklievaart.toolkit.io.api.http;

import java.io.InputStream;
import java.util.List;

public interface HttpClient {

	public String getString(String url);

	public List<String> getLines(String url);

	public InputStream getInputStream(String url);
}
