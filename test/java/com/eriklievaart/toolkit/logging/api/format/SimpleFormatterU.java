package com.eriklievaart.toolkit.logging.api.format;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;

public class SimpleFormatterU {

	@Test
	public void format() {
		LogRecord record = new LogRecord(Level.FINE, "foo");
		record.setLoggerName("com.eriklievaart.bar");

		String actual = new SimpleFormatter().format(record).replaceFirst("[ ]{2,}", " ");
		Check.isEqual(actual, "DEBUG bar foo");
	}

	@Test
	public void formatLoggerName() {
		Check.isEqual(SimpleFormatter.formatLoggerName(null), "<null>");
		Check.isEqual(SimpleFormatter.formatLoggerName("com.eriklievaart.Bla"), "Bla");
		Check.isEqual(SimpleFormatter.formatLoggerName("com.eriklievaart.a.b.c.Bla"), "Bla");
		Check.isEqual(SimpleFormatter.formatLoggerName("virtual.ignore.me"), "virtual.ignore.me");
	}
}
