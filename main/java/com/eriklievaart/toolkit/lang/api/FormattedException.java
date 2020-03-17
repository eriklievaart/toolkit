package com.eriklievaart.toolkit.lang.api;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class FormattedException extends RuntimeException {

	public FormattedException(final String format, final Object... args) {
		super(Str.sub(format, args));
	}

	public FormattedException(final String format, final Throwable cause, final Object... args) {
		super(Str.sub(format, args), cause);
	}

	public static void on(final boolean condition, final String format, final Object... args) {
		if (condition) {
			throw new FormattedException(format, args);
		}
	}

	public Throwable getRootCause() {
		return ThrowableTool.getRootCause(this);
	}
}