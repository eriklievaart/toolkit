package com.eriklievaart.toolkit.runtime.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;

class StreamRunnable implements Runnable {

	private final BufferedReader reader;
	private final CliOutput out;

	public StreamRunnable(final InputStream input, final CliOutput out) {
		this.reader = new BufferedReader(new InputStreamReader(input));
		this.out = out;
	}

	@Override
	public void run() {
		try {
			String message = reader.readLine();
			while (message != null) {
				out.println(message);
				message = reader.readLine();
			}

		} catch (Exception e) {
			throw new RuntimeIOException("Reading stream interrupted: " + e.getMessage());
		}
	}
}