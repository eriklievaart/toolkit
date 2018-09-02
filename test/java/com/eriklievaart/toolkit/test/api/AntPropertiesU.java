package com.eriklievaart.toolkit.test.api;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.test.api.AntProperties;

public class AntPropertiesU {

	@Test
	public void isTestMode() {
		Check.isTrue(AntProperties.isTestMode());
	}
}
