package com.eriklievaart.toolkit.convert.api;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class ConversionException extends RuntimeException {

	public ConversionException(String msg) {
		super(msg);
	}

	public static void on(boolean b, String format, Object... args) throws ConversionException {
		if (b) {
			throw new ConversionException(Str.sub(format, args));
		}
	}

	public static void unless(boolean b, String format, Object... args) throws ConversionException {
		on(!b, format, args);
	}
}
