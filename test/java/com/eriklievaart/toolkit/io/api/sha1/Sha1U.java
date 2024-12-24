package com.eriklievaart.toolkit.io.api.sha1;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class Sha1U {

	@Test
	public void hash() {
		String hash = Sha1.hash("hello world!");
		Check.isEqual(hash, "430ce34d020724ed75a196dfc2ad67c77772d169");
	}
}