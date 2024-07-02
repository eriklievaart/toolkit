package com.eriklievaart.toolkit.io.api.sha1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Sha1OutputStream extends OutputStream {

	private OutputStream delegate;
	private Sha1Digest digest = new Sha1Digest();

	public Sha1OutputStream(OutputStream delegate) {
		this.delegate = delegate;
	}

	public Sha1OutputStream(File file) throws FileNotFoundException {
		this.delegate = new FileOutputStream(file);
	}

	@Override
	public void write(int b) throws IOException {
		digest.update((byte) b);
		delegate.write(b);
	}

	public String getHash() {
		return digest.getHash();
	}
}
