package com.eriklievaart.toolkit.io.api.sha1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Sha1InputStream extends InputStream {

	private InputStream delegate;
	private Sha1Digest digest = new Sha1Digest();

	public Sha1InputStream(InputStream delegate) {
		this.delegate = delegate;
	}

	public Sha1InputStream(File file) throws FileNotFoundException {
		this.delegate = new FileInputStream(file);
	}

	@Override
	public int read() throws IOException {
		int b = delegate.read();
		if (b != -1) {
			digest.update((byte) b);
		}
		return b;
	}

	public String getHash() {
		return digest.getHash();
	}
}
