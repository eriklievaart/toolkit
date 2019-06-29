package com.eriklievaart.toolkit.io.api.http;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class RotatingHttpClient implements HttpClient {

	private AtomicLong rotate = new AtomicLong();
	private List<HttpClient> clients = NewCollection.list();

	public RotatingHttpClient(HttpClient... robin) {
		for (HttpClient client : robin) {
			clients.add(client);
		}
	}

	@Override
	public String getString(String url) {
		return getDelegate().getString(url);
	}

	@Override
	public List<String> getLines(String url) {
		return getDelegate().getLines(url);
	}

	@Override
	public InputStream getInputStream(String url) {
		return getDelegate().getInputStream(url);
	}

	@Override
	public InputStream getInputStream(HttpCall call) {
		return getDelegate().getInputStream(call);
	}

	@Override
	public void setHeader(String name, String value) {
		for (HttpClient client : clients) {
			client.setHeader(name, value);
		}
	}

	@Override
	public HttpClient socks5(String ip, int port) {
		throw new UnsupportedOperationException("Call this method on the delegates");
	}

	public void rotate() {
		rotate.incrementAndGet();
	}

	private HttpClient getDelegate() {
		int clientIndex = rotate.intValue() % clients.size();
		return clients.get(clientIndex);
	}
}
