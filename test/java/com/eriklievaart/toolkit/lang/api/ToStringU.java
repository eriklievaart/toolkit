package com.eriklievaart.toolkit.lang.api;

import java.util.function.Supplier;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;

public class ToStringU {

	@Test
	public void simple() {
		CheckStr.isEqual(ToString.simple(this, "${%}", "Full Moon wo Sagashite"),
				"ToStringU{`Full Moon wo Sagashite`}");
	}

	@Test
	public void objectSupplier() {
		String actual = ToString.object(new Supplier<Integer>() {
			@Override
			public Integer get() {
				return 123;
			}
		});
		Check.isEqual(actual, "123");
	}

	@Test
	public void objectArray() {
		String actual = ToString.object(new int[] { 1, 2, 3 });
		Check.isEqual(actual, "[1, 2, 3]");
	}
}