package com.eriklievaart.toolkit.lang.api;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.ToString;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;

public class ToStringU {

	@Test
	public void simple() {
		CheckStr.isEqual(ToString.simple(this, "${%}", "Full Moon wo Sagashite"), "ToStringU{`Full Moon wo Sagashite`}");
	}
}