package com.eriklievaart.toolkit.io.api;

import java.io.File;

public class SystemProperties {
	private static final String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isSet(String key, String value) {
		String actual = System.getProperty(key);
		if (actual == null || value == null) {
			return actual == value;
		}
		return actual.equalsIgnoreCase(value);
	}

	public static boolean isWindows() {
		return OS.indexOf("win") >= 0;
	}

	public static boolean isMac() {
		return OS.indexOf("mac") >= 0;
	}

	public static boolean isUnix() {
		return OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0;
	}

	public static File getHomeDir() {
		return new File(System.getProperty("user.home"));
	}
}
