package com.eriklievaart.toolkit.logging.api;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.logging.api.appender.CollectionAppender;

public class AbstractAppenderU {

	@Test
	public void isLevelEnabled() {
		try (CollectionAppender appender = new CollectionAppender()) {
			appender.setLevel(Level.INFO);

			Check.isFalse(appender.isLevelEnabled(Level.FINEST));
			Check.isFalse(appender.isLevelEnabled(Level.FINE));
			Check.isTrue(appender.isLevelEnabled(Level.INFO));
			Check.isTrue(appender.isLevelEnabled(Level.WARNING));
			Check.isTrue(appender.isLevelEnabled(Level.SEVERE));

			appender.append(new LogRecord(Level.FINEST, "ignore finest"));
			Check.isTrue(appender.isEmpty());

			appender.append(new LogRecord(Level.FINE, "ignore fine"));
			Check.isTrue(appender.isEmpty());

			appender.append(new LogRecord(Level.INFO, "write info"));
			CheckStr.contains(appender.popMessage(), "write info");

			appender.append(new LogRecord(Level.WARNING, "write warning"));
			CheckStr.contains(appender.popMessage(), "write warning");

			appender.append(new LogRecord(Level.SEVERE, "write severe"));
			CheckStr.contains(appender.popMessage(), "write severe");
		}
	}
}