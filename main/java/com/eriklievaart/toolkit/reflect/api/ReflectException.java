package com.eriklievaart.toolkit.reflect.api;

import com.eriklievaart.toolkit.lang.api.Showable;
import com.eriklievaart.toolkit.lang.api.str.Str;

/**
 * This RuntimeException can be thrown whenever a problem occurs with Java reflection.
 *
 * @author Erik Lievaart
 */
public class ReflectException extends RuntimeException implements Showable {

	/**
	 * Constructor with detail message.
	 */
	public ReflectException(final String msg) {
		super(msg);
	}

	/**
	 * Constructor with detail message and cause.
	 */
	public ReflectException(final String msg, final Throwable t) {
		super(msg, t);
	}

	/**
	 * Constructor with formatted detail message and arguments to the message.
	 */
	public ReflectException(final String format, final Object... args) {
		this(Str.sub(format, args));
	}

	/**
	 * Throw a ReflectException with the specified message format and arguments if the condition is true.
	 */
	public static void on(final boolean condition, final String format, final Object... args) {
		if (condition) {
			throw new ReflectException(Str.sub(format, args));
		}
	}

	/**
	 * Throw a ReflectException with the specified message format and arguments if the condition is false.
	 */
	public static void unless(final boolean condition, final String format, final Object... args) {
		if (!condition) {
			throw new ReflectException(Str.sub(format, args));
		}
	}

	@Override
	public String getDisplayLabel() {
		return getMessage();
	}
}
