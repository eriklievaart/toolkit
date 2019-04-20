package com.eriklievaart.toolkit.test.api;

import org.junit.Test;

import com.eriklievaart.toolkit.ant.api.AntProperties;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class AntPropertiesU {

	@Test
	public void isTestMode() {
		Check.isTrue(AntProperties.isTestMode());
	}
}
