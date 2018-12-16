package com.eriklievaart.toolkit.io.api;

import com.eriklievaart.toolkit.lang.api.str.Str;

/**
 * RuntimeException version of the IOException.
 * 
 * @author Erik Lievaart
 */
public class RuntimeIOException extends RuntimeException {

	/**
	 * Create an Exception, creates a message using the format and arguments.
	 * 
	 * @see Str#sub(String, Object...)
	 */
	public RuntimeIOException(final String format, final Object... args) {
		super(Str.sub(format, args));
	}

	/**
	 * Create an Exception with a root cause, creates a message using the format and arguments.
	 * 
	 * @see Str#sub(String, Object...)
	 */
	public RuntimeIOException(final String format, final Throwable t, final Object... args) {
		super(Str.sub(format, args), t);
	}

	/**
	 * Create an Exception with a message and a root cause.
	 */
	public RuntimeIOException(final String msg, final Throwable t) {
		super(msg, t);
	}

	/**
	 * Create an Exception with a root cause.
	 */
	public RuntimeIOException(final Throwable t) {
		super(t.getMessage(), t);
	}

	/**
	 * Throw an exception if the flag is true.
	 * 
	 * @see Str#sub(String, Object...)
	 */
	public static void on(final boolean b, final String format, final Object... args) {
		if (b) {
			throw new RuntimeIOException(format, args);
		}
	}

	/**
	 * Throw an exception if the flag is false.
	 * 
	 * @see Str#sub(String, Object...)
	 */
	public static void unless(final boolean b, final String format, final Object... args) {
		if (!b) {
			throw new RuntimeIOException(format, args);
		}
	}
}
