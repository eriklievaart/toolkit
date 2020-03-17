package com.eriklievaart.toolkit.io.api.http;

import java.io.InputStream;
import java.util.List;

import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class LoggingHttpClient implements HttpClient {
	private final LogTemplate log = new LogTemplate(getClass());

	private final HttpClient delegate;

	public LoggingHttpClient() {
		this(new SimpleHttpClient());
	}

	public LoggingHttpClient(HttpClient delegate) {
		this.delegate = delegate;
	}

	@Override
	public String getString(String url) {
		log.debug("getString(%)", url);
		String html = delegate.getString(url);
		log.trace(html);
		return html;
	}

	@Override
	public List<String> getLines(String url) {
		log.debug("getLines(%)", url);
		List<String> lines = delegate.getLines(url);
		if (log.isTraceEnabled()) {
			log.trace(Str.joinLines(lines));
		}
		return lines;
	}

	@Override
	public void setHeader(String name, String value) {
		log.debug("setHeader(%, %)", name, value);
		delegate.setHeader(name, value);
	}

	@Override
	public InputStream getInputStream(String url) {
		log.debug("getInputStream(%)", url);
		return delegate.getInputStream(url);
	}

	@Override
	public InputStream getInputStream(HttpCall call) {
		log.debug("getInputStream(%)", call);
		return delegate.getInputStream(call);
	}

	@Override
	public LoggingHttpClient socks5(String ip, int port) {
		log.debug("socks5($:$)", ip, port);
		delegate.socks5(ip, port);
		return this;
	}
}