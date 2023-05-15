package com.eriklievaart.toolkit.logging.api;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.appender.Appender;
import com.eriklievaart.toolkit.logging.api.level.LogLevelConfig;

class Logger {

	private String name;
	private LogLevelConfig levels;

	Logger(String name, LogLevelConfig levels) {
		this.name = name;
		this.levels = levels;
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

	private void append(final LogRecord record) {
		record.setLoggerName(name);
		LogConfig.getLoggingExecutor().execute(() -> {
			for (Appender appender : LogConfig.getAppenders(name)) {
				try {
					appender.append(record);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Check if the log level is enabled. This is true IFF the logger is enabled for "test" level AND there is at least
	 * one appender at "test" level.
	 */
	private boolean isLevelEnabled(Level test) {
		return levels.isLevelEnabled(name, test);
	}
}