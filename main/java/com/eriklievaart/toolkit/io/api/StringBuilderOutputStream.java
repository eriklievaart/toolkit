package com.eriklievaart.toolkit.io.api;

import java.io.IOException;
import java.io.OutputStream;

/**
 * OutputStream that writes to a StringBuilder. Useful for converting OutputStream's into an in memory String.
 *
 * @author Erik Lievaart
 */
public class StringBuilderOutputStream extends OutputStream {

	private final StringBuilder builder = new StringBuilder();

	@Override
	public void write(final int b) throws IOException {
		builder.append((char) b);
	}

	/**
	 * Return all the data currently written to this OutputStream as a String.
	 */
	public String getResult() {
		return builder.toString();
	}
}
