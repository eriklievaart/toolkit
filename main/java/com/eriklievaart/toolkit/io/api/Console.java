package com.eriklievaart.toolkit.io.api;

import com.eriklievaart.toolkit.lang.api.str.Str;

public class Console {

	public static void println(String message) {
		System.out.println(message);
	}

	public static void println(String format, Object... args) {
		System.out.println(Str.sub(format, args));
	}
}