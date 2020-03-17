package com.eriklievaart.toolkit.reflect.api.mapper;

import java.awt.Point;
import java.util.Map;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckCollection;
import com.eriklievaart.toolkit.lang.api.collection.MapTool;
import com.eriklievaart.toolkit.reflect.api.ReflectException;
import com.eriklievaart.toolkit.reflect.mapper.MappedInteger;
import com.eriklievaart.toolkit.reflect.mapper.MappedString;

public class ObjectMapperU {

	@Test
	public void toMapString() {
		MappedString mapped = new MappedString();
		mapped.string = "test";

		Map<String, String> map = ObjectMapper.toMap(mapped);
		CheckCollection.isSize(map, 1);
		Check.isEqual(map.get("string"), "test");
	}

	@Test
	public void toMapInteger() {
		MappedInteger mapped = new MappedInteger();
		mapped.integer = 5;

		Map<String, String> map = ObjectMapper.toMap(mapped);
		CheckCollection.isSize(map, 1);
		Check.isEqual(map.get("integer"), "5");
	}

	@Test
	public void injectString() {
		MappedString mapped = new MappedString();
		ObjectMapper.inject(mapped, MapTool.of("string", "test"));
		Check.isEqual(mapped.string, "test");
	}

	@Test
	public void injectInteger() {
		MappedInteger mapped = new MappedInteger();
		ObjectMapper.inject(mapped, MapTool.of("integer", "5"));
		Check.isEqual(mapped.integer, 5);
	}

	@Test(expected = ReflectException.class)
	public void toMapNoProperties() {
		ObjectMapper.toMap(new Point());
	}
}