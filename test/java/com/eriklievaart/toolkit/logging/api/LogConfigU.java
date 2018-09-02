package com.eriklievaart.toolkit.logging.api;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class LogConfigU {

	@Test
	public void getParent() throws Exception {
		String logger = "parent.child";
		Check.isEqual(LogConfig.getParent(logger), "parent");
	}

	@Test
	public void getParentRoot() throws Exception {
		String logger = "root";
		Check.isEqual(LogConfig.getParent(logger), "");
	}
}
