package com.eriklievaart.toolkit.convert.api.construct;

import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.convert.api.ConversionException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;

public class MapConstructorU {

	@Test
	public void createMapSingle() {
		Map<?, ?> map = new MapConstructor().constructObject("key=value");
		Check.isEqual(map, MapTool.of("key", "value"));
	}

	@Test
	public void createMapDouble() {
		Map<?, ?> map = new MapConstructor().constructObject("key1=value1#key2=value2");
		Check.isEqual(map, MapTool.of("key1", "value1", "key2", "value2"));
	}

	@Test(expected = ConversionException.class)
	public void createMapFailure() throws ConversionException {
		new MapConstructor().createConverter().convertToObject("key1=");
	}
}