package com.eriklievaart.toolkit.logging.api;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class LogConfigU {

	@Test
	public void getParent() {
		Check.isEqual(LogConfig.getParent("com.apache.commons.lang"), "com.apache.commons");
		Check.isEqual(LogConfig.getParent("com.apache.commons"), "com.apache");
		Check.isEqual(LogConfig.getParent("com.apache"), "com");
		Check.isEqual(LogConfig.getParent("com"), "");
	}
}