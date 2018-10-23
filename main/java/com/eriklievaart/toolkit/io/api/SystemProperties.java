package com.eriklievaart.toolkit.io.api;

public class SystemProperties {

	public static boolean isSet(String key, String value) {
		String actual = System.getProperty(key);
		if (actual == null || value == null) {
			return actual == value;
		}
		return actual.equalsIgnoreCase(value);
	}
}
