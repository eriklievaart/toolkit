package com.eriklievaart.toolkit.io.api.http;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class HttpDirectoryCacheU {

	@Test
	public void encode() {
		Check.isEqual(HttpDirectoryCache.encode("https://example.com/nested"), "example.com_nested");
	}
}
