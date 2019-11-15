package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class OptionalToolU {

	@Test
	public void notNull() {
		Check.isEqual(OptionalTool.notNull("something").get(), "something");
		Check.isFalse(OptionalTool.notNull(null).isPresent());
	}

	@Test
	public void mapValue() {
		Map<String, String> map = NewCollection.map();
		map.put("abc", "123");

		Check.isEqual(OptionalTool.mapValue(map, "abc").get(), "123");
		Check.isFalse(OptionalTool.mapValue(map, "bcd").isPresent());
	}
}
