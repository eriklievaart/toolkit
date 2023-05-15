package com.eriklievaart.toolkit.logging.api.level;

import java.util.logging.Level;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.logging.api.LogConfig;

public class LogLevelConfigU {

	@Test
	public void getConfiguredLevel() {
		LogConfig.installDefaultRootAppenders("");
		LogLevelConfig testable = new LogLevelConfig();

		testable.setLevel("com", Level.INFO);
		Check.isEqual(testable.getConfiguredLevel(""), Level.FINEST);
		Check.isEqual(testable.getConfiguredLevel("com"), Level.INFO);
		Check.isEqual(testable.getConfiguredLevel("com.apache"), Level.INFO);
		Check.isEqual(testable.getConfiguredLevel("com.apache.commons"), Level.INFO);
		Check.isEqual(testable.getConfiguredLevel("com.apache.commons.lang"), Level.INFO);

		testable.setLevel("com.apache.commons", Level.FINE);
		Check.isEqual(testable.getConfiguredLevel(""), Level.FINEST);
		Check.isEqual(testable.getConfiguredLevel("com"), Level.INFO);
		Check.isEqual(testable.getConfiguredLevel("com.apache"), Level.INFO);
		Check.isEqual(testable.getConfiguredLevel("com.apache.commons"), Level.FINE);
		Check.isEqual(testable.getConfiguredLevel("com.apache.commons.lang"), Level.FINE);
	}

	@Test
	public void isLevelEnabled() {
		LogConfig.installDefaultRootAppenders("");
		LogLevelConfig testable = new LogLevelConfig();

		testable.setLevel("com", Level.INFO);

		Check.isTrue(testable.isLevelEnabled("", Level.FINEST));
		Check.isTrue(testable.isLevelEnabled("", Level.FINER));
		Check.isTrue(testable.isLevelEnabled("", Level.FINE));
		Check.isTrue(testable.isLevelEnabled("", Level.INFO));
		Check.isTrue(testable.isLevelEnabled("", Level.WARNING));
		Check.isTrue(testable.isLevelEnabled("", Level.SEVERE));

		Check.isFalse(testable.isLevelEnabled("com", Level.FINEST));
		Check.isFalse(testable.isLevelEnabled("com", Level.FINER));
		Check.isFalse(testable.isLevelEnabled("com", Level.FINE));
		Check.isTrue(testable.isLevelEnabled("com", Level.INFO));
		Check.isTrue(testable.isLevelEnabled("com", Level.WARNING));
		Check.isTrue(testable.isLevelEnabled("com", Level.SEVERE));

		Check.isFalse(testable.isLevelEnabled("com.apache", Level.FINEST));
		Check.isFalse(testable.isLevelEnabled("com.apache", Level.FINER));
		Check.isFalse(testable.isLevelEnabled("com.apache", Level.FINE));
		Check.isTrue(testable.isLevelEnabled("com.apache", Level.INFO));
		Check.isTrue(testable.isLevelEnabled("com.apache", Level.WARNING));
		Check.isTrue(testable.isLevelEnabled("com.apache", Level.SEVERE));
	}

	@Test
	public void isLevelEnabledAppenders() {
		LogLevelConfig testable = new LogLevelConfig();

		testable.setLevel("com", Level.FINER);

		Check.isFalse(testable.isLevelEnabled("com", Level.FINEST));
		Check.isTrue(testable.isLevelEnabled("com", Level.FINER));
		Check.isTrue(testable.isLevelEnabled("com", Level.FINE));

		LogConfig.installDefaultRootAppenders("");
	}
}
