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

		String expected = "DEBUG ~bar    foo";
		String actual = new SimpleFormatter().format(record);
		Check.isEqual(actual, expected);
	}
}
