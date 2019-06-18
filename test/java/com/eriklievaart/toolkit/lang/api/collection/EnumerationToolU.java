package com.eriklievaart.toolkit.lang.api.collection;

import java.util.Enumeration;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class EnumerationToolU {

	@Test
	public void of() {
		Enumeration<String> enumeration = EnumerationTool.of("foo", "bar");

		Check.isTrue(enumeration.hasMoreElements());
		Check.isEqual(enumeration.nextElement(), "foo");

		Check.isTrue(enumeration.hasMoreElements());
		Check.isEqual(enumeration.nextElement(), "bar");

		Check.isFalse(enumeration.hasMoreElements());
	}

	@Test
	public void contains() {
		Check.isTrue(EnumerationTool.contains(EnumerationTool.of("foo", "bar", null), "foo"));
		Check.isTrue(EnumerationTool.contains(EnumerationTool.of("foo", "bar", null), "bar"));
		Check.isTrue(EnumerationTool.contains(EnumerationTool.of("foo", "bar", null), null));
		Check.isFalse(EnumerationTool.contains(EnumerationTool.of("foo", "bar", null), "baz"));
	}
}
