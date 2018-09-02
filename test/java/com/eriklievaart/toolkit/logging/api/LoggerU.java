package com.eriklievaart.toolkit.logging.api;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Before;
import org.junit.Test;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.check.CheckStr;
import com.eriklievaart.toolkit.logging.api.appender.AbstractAppender;
import com.eriklievaart.toolkit.logging.api.appender.CollectionAppender;

public class LoggerU {

	@Before
	public void setup() {
		LogConfig.init();
	}

	@Test
	public void logObject() {
		LogRecord record = new LogRecord(Level.WARNING, "expecting123");
		String logged = logRecord(record);
		CheckStr.contains(logged, "WARN");
		CheckStr.contains(logged, "expecting123");
	}

	@Test
	public void logLoggerName() {
		LogRecord record = new LogRecord(Level.INFO, "my message");
		Logger logger = new Logger("test.logger");

		String result = logRecord(logger, record);

		CheckStr.contains(result, "INFO");
		CheckStr.contains(result, "test.logger");
	}

	@Test
	public void logException() {
		LogRecord record = new LogRecord(Level.SEVERE, "stack trace");
		record.setThrown(new Exception("#oops#"));
		String logged = logRecord(record);

		CheckStr.contains(logged, getClass().getName()); // current file should be in stack trace
		CheckStr.contains(logged, "ERROR");
		CheckStr.contains(logged, "java.lang.Exception => #oops#");
	}

	@Test
	public void logCause() {
		LogRecord record = new LogRecord(Level.SEVERE, "stack trace");
		record.setThrown(new Exception("#parent#", new Error("#child#")));
		String logged = logRecord(record);

		CheckStr.contains(logged, getClass().getName()); // current file should be in stack trace
		CheckStr.contains(logged, "java.lang.Exception => #parent#");
		CheckStr.contains(logged, "java.lang.Error => #child#");
	}

	@Test
	public void logFormat() {
		LogConfig.setDefaultFormatter(new Formatter() {
			@Override
			public String format(LogRecord record) {
				return "custom message format";
			}
		});
		String message = logRecord(new LogRecord(Level.WARNING, "custom formatter"));
		Check.isEqual(message, "custom message format");
	}

	@Test
	public void logAppenderOverride() throws Exception {
		BufferAppender appender = new BufferAppender();
		LogConfig.setAppenders("override", Arrays.asList(appender));

		LogTemplate override = new LogTemplate("override");
		override.info("buffered");
		Check.isEqual(appender.buffer, "buffered");
	}

	@Test
	public void logAppenderOverrideParentUnaffected() throws Exception {
		BufferAppender appender = new BufferAppender();
		LogConfig.setAppenders("parent.child", Arrays.asList(appender));

		LogTemplate parent = new LogTemplate("parent");
		parent.info("should not be buffered");
		Check.isNull(appender.buffer);
	}

	@Test
	public void logAppenderOverrideChildAffected() throws Exception {
		BufferAppender appender = new BufferAppender();
		LogConfig.setAppenders("parent", Arrays.asList(appender));

		LogTemplate child = new LogTemplate("parent.child");
		child.info("child should inherit");
		Check.isEqual(appender.buffer, "child should inherit");
	}

	private String logRecord(LogRecord record) {
		return logRecord(new Logger("test.logger.id"), record);
	}

	private String logRecord(Logger logger, LogRecord record) {
		CollectionAppender appender = new CollectionAppender();
		LogConfig.setDefaultAppenders(appender);

		logger.log(record);
		return appender.popMessage();
	}

	private class BufferAppender extends AbstractAppender {
		private String buffer;

		@Override
		public void append(LogRecord record) {
			buffer = record.getMessage();
		}
	}
}