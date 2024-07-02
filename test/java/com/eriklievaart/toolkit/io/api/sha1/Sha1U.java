package com.eriklievaart.toolkit.io.api.sha1;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import com.eriklievaart.toolkit.io.api.StreamTool;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class Sha1U {

	@Test
	public void hash() {
		String hash = Sha1.hash("hello world!");
		Check.isEqual(hash, "430ce34d020724ed75a196dfc2ad67c77772d169");
	}

	@Test
	public void copyAndHash() {
		String data = "hello world!";
		String expectedHash = "430ce34d020724ed75a196dfc2ad67c77772d169";

		Sha1InputStream is = new Sha1InputStream(StreamTool.toInputStream(data));
		Sha1OutputStream os = new Sha1OutputStream(new ByteArrayOutputStream());
		String hash = Sha1.sha1CopyAndHash(is, os);

		Check.isEqual(hash, expectedHash);
		Check.isEqual(is.getHash(), expectedHash);
		Check.isEqual(os.getHash(), expectedHash);
	}
}