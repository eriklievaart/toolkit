package com.eriklievaart.toolkit.logging.api;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.str.Str;

/**
 * Utility class for writing log messages with arguments.
 *
 * @author Erik Lievaart
 */
public class LogTemplate {

	private final Logger log;

	/**
	 * Create a LogTemplate for the given name.
	 */
	public LogTemplate(final String name) {
		this.log = LogConfig.getLogger(name);
	}

	/**
	 * Create a LogTemplate for the given literal.
	 */
	public LogTemplate(final Class<?> literal) {
		this.log = LogConfig.getLogger(literal.getCanonicalName());
	}

	/**
	 * Is trace logging enabled flag.
	 */
	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}

	/**
	 * Is debug logging enabled flag.
	 */
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	/**
	 * Is info logging enabled flag.
	 */
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	/**
	 * Is warn logging enabled flag.
	 */
	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}

	/**
	 * Write a trace level message (ignores formatting percentiles '%').
	 */
	public void trace(final Object message) {
		if (log.isTraceEnabled()) {
			log.log(createLogRecord(Level.FINEST, message));
		}
	}

	/**
	 * Write a trace level message with the specified root cause (ignores formatting percentiles '%').
	 */
	public void trace(final String message, final Throwable thrown) {
		if (log.isTraceEnabled()) {
			log.log(createLogRecord(Level.FINEST, thrown, message));
		}
	}

	/**
	 * Write a formatted trace level message.
	 *
	 * @see Str#sub(String, Object...)
	 */
	public void trace(final String format, final Object... args) {
		if (log.isTraceEnabled()) {
			log.log(createLogRecord(Level.FINEST, format), args);
		}
	}

	/**
	 * Write a debug level message (ignores formatting percentiles '%').
	 */
	public void debug(final Object message) {
		if (log.isDebugEnabled()) {
			log.log(createLogRecord(Level.FINE, message));
		}
	}

	/**
	 * Write a debug level message with the specified root cause (ignores formatting percentiles '%').
	 */
	public void debug(final String message, final Throwable thrown) {
		if (log.isDebugEnabled()) {
			log.log(createLogRecord(Level.FINE, thrown, message));
		}
	}

	/**
	 * Write a formatted debug level message.
	 *
	 * @see Str#sub(String, Object...)
	 */
	public void debug(final String format, final Object... args) {
		if (log.isDebugEnabled()) {
			log.log(createLogRecord(Level.FINE, format), args);
		}
	}

	/**
	 * Write a info level message (ignores formatting percentiles '%').
	 */
	public void info(final Object message) {
		if (log.isInfoEnabled()) {
			log.log(createLogRecord(Level.INFO, message));
		}
	}

	/**
	 * Write a info level message with the specified root cause (ignores formatting percentiles '%').
	 */
	public void info(final String message, final Throwable thrown) {
		if (log.isInfoEnabled()) {
			log.log(createLogRecord(Level.INFO, thrown, message));
		}
	}

	/**
	 * Write a formatted info level message.
	 *
	 * @see Str#sub(String, Object...)
	 */
	public void info(final String format, final Object... args) {
		if (log.isInfoEnabled()) {
			log.log(createLogRecord(Level.INFO, format), args);
		}
	}

	/**
	 * Write a warn level message (ignores formatting percentiles '%').
	 */
	public void warn(final Object message) {
		if (log.isWarnEnabled()) {
			log.log(createLogRecord(Level.WARNING, message));
		}
	}

	/**
	 * Write a warn level message with the specified root cause (ignores formatting percentiles '%').
	 */
	public void warn(final String message, final Throwable thrown) {
		if (log.isWarnEnabled()) {
			log.log(createLogRecord(Level.WARNING, thrown, message));
		}
	}

	/**
	 * Write a formatted warn level message.
	 *
	 * @see Str#sub(String, Object...)
	 */
	public void warn(final String format, final Object... args) {
		if (log.isWarnEnabled()) {
			log.log(createLogRecord(Level.WARNING, format), args);
		}
	}

	/**
	 * Write a warn level message with the specified root cause (ignores formatting percentiles '%').
	 */
	public void warn(final String format, final Throwable thrown, final Object... args) {
		if (log.isWarnEnabled()) {
			log.log(createLogRecord(Level.WARNING, thrown, Str.sub(format, args)));
		}
	}

	/**
	 * Write a error level message with the specified root cause (ignores formatting percentiles '%').
	 */
	public void error(final Object message, final Throwable thrown) {
		log.log(createLogRecord(Level.SEVERE, thrown, message));
	}

	/**
	 * Write a formatted error level message.
	 *
	 * @see Str#sub(String, Object...)
	 */
	public void error(final String format, Object... args) {
		log.log(createLogRecord(Level.SEVERE, format), args);
	}

	/**
	 * Write a formatted error level message.
	 *
	 * @see Str#sub(String, Object...)
	 */
	public void error(final String format, final Throwable thrown, Object... args) {
		log.log(createLogRecord(Level.SEVERE, thrown, format), args);
	}

	/**
	 * Log a formatted message with a programmatically assignable level.
	 */
	public void log(Level level, String format, Object... args) {
		log.log(createLogRecord(level, format), args);
	}

	private LogRecord createLogRecord(Level level, Object message) {
		Throwable t = message instanceof Throwable ? (Throwable) message : null;
		return createLogRecord(level, t, message);
	}

	private LogRecord createLogRecord(Level level, Throwable thrown, Object message) {
		LogRecord record = new LogRecord(level, message == null ? "LogTemplate: <null>" : message.toString());
		record.setThrown(thrown);
		return record;
	}
}