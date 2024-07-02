package com.eriklievaart.toolkit.io.api.sha1;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class Sha1DigestU {

	@Test
	public void hash() {
		Sha1Digest digest = new Sha1Digest();

		byte[] bytes = new String("hello world!").getBytes();
		digest.update(bytes, 0, bytes.length);
		Check.isEqual(digest.getHash(), "430ce34d020724ed75a196dfc2ad67c77772d169");
	}
}
