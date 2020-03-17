package com.eriklievaart.toolkit.io.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class StreamToolU {

	@Test
	public void toStringEncoded() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] { -61, -77 });
		String result = StreamTool.toString(bais, "UTF-8");
		Check.isEqual(result, "ó");
	}
}