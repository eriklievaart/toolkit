package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;

public class MapToolU {

	@Test
	public void of() {
		Map<String, String> map = MapTool.of("key", "value");
		Check.isEqual(map.get("key"), "value");
	}
}
