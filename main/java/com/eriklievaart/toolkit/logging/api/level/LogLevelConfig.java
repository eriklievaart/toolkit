package com.eriklievaart.toolkit.logging.api.level;

import java.util.Map;
import java.util.logging.Level;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.logging.api.LogConfig;
import com.eriklievaart.toolkit.logging.api.appender.Appender;

public class LogLevelConfig {

	private Map<String, Level> config = NewCollection.concurrentMap();

	public void setLevel(String pkg, Level level) {
		Check.notNull(pkg);

		if (level == null) {
			config.remove(pkg);
		} else {
			config.put(pkg, level);
		}
	}

	public boolean isLevelEnabled(String pkg, Level test) {
		if (test.intValue() >= getConfiguredLevel(pkg).intValue()) {
			for (Appender appender : LogConfig.getAppenders(pkg)) {
				if (appender.isLevelEnabled(test)) {
					return true;
				}
			}
		}
		return false;
	}

	Level getConfiguredLevel(String pkg) {
		Check.notNull(pkg, "argument cannot be null, use an empty String for root package.");
		if (config.isEmpty()) {
			return Level.FINEST;
		}
		if (config.containsKey(pkg)) {
			return config.get(pkg);
		}
		return pkg.equals("") ? Level.FINEST : getConfiguredLevel(LogConfig.getParent(pkg));
	}
}
