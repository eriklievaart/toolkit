package com.eriklievaart.toolkit.lang.api.str;

import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;

public class TokenReplacerU {

	@Test
	public void sub() {
		Map<String, String> tokens = MapTool.of("a", "1", "b", "2");
		String result = TokenReplacer.sub("foo @a@ bar @B@ baz @ignore@", tokens);
		Check.isEqual(result, "foo 1 bar 2 baz @ignore@");
	}
}
