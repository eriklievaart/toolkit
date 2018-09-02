package com.eriklievaart.toolkit.logging.api;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.appender.Appender;

public class Logger {

	private String name;
	private AtomicReference<Level> levelReference = new AtomicReference<>(Level.FINEST);

	Logger(String name) {
		this.name = name;
	}

	public String getLoggerName() {
		return name;
	}

	public boolean isTraceEnabled() {
		return isLevelEnabled(Level.FINEST);
	}

	public boolean isDebugEnabled() {
		return isLevelEnabled(Level.FINE);
	}

	public boolean isInfoEnabled() {
		return isLevelEnabled(Level.INFO);
	}

	public boolean isWarnEnabled() {
		return isLevelEnabled(Level.WARNING);
	}

	public void setLevel(Level level) {
		levelReference.set(level);
	}

	public void log(LogRecord record) {
		if (!isLevelEnabled(record.getLevel())) {
			return;
		}
		append(record);
	}

	public void log(LogRecord record, Object... args) {
		if (!isLevelEnabled(record.getLevel())) {
			return;
		}
		record.setMessage(Str.sub(record.getMessage(), args));
		append(record);
	}

	private void append(LogRecord record) {
		record.setLoggerName(name);
		for (Appender appender : LogConfig.getAppenders(name)) {
			try {
				appender.append(record);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Check if the log level is enabled. This is true IFF the logger is enabled for "test" level AND there is at least
	 * one appender at "test" level.
	 */
	private boolean isLevelEnabled(Level test) {
		if (test.intValue() >= levelReference.get().intValue()) {
			for (Appender appender : LogConfig.getAppenders(name)) {
				if (appender.isLevelEnabled(test)) {
					return true;
				}
			}
		}
		return false;
	}
}