package com.eriklievaart.toolkit.logging.api.format;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.date.SimpleDateFormatFactory;

public class DatedFormatterU {

	@Test
	public void format() {
		LogRecord record = new LogRecord(Level.FINE, "foo");
		record.setLoggerName("com.example.bar");

		String expectedDate = SimpleDateFormatFactory.getDateFormatNL().format(new Date());
		String expected = expectedDate + " DEBUG    com.example.bar    foo";

		String actual = new DatedFormatter("dd-MM-yyyy", new SimpleFormatter()).format(record);
		Check.isEqual(actual, expected);
	}
}
