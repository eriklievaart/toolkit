package com.eriklievaart.toolkit.lang.api;

import com.eriklievaart.toolkit.lang.api.str.Str;


public class AssertionException extends RuntimeException {

	public AssertionException(final String msg) {
		super(msg);
	}

	public AssertionException(final String format, final Object... args) {
		super(Str.sub(format, args));
	}

	public static void on(final boolean condition, final String format, final Object... args) {
		if (condition) {
			throw new AssertionException(Str.sub(format, args));
		}
	}

	public static void unless(final boolean condition, final String format, final Object... args) {
		if (!condition) {
			throw new AssertionException(Str.sub(format, args));
		}
	}

}
