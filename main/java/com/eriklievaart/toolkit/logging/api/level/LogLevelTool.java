package com.eriklievaart.toolkit.logging.api.level;

import java.util.logging.Level;

import com.eriklievaart.toolkit.io.api.RuntimeIOException;
import com.eriklievaart.toolkit.lang.api.check.Check;

public class LogLevelTool {

	public static Level toLevel(String value) {
		Check.notBlank(value);
		switch (value.toUpperCase().trim()) {

		case "FINEST":
		case "FINER":
		case "TRACE":
			return Level.FINEST;

		case "DEBUG":
		case "FINE":
			return Level.FINE;

		case "INFO":
		case "CONFIG":
			return Level.INFO;

		case "WARN":
		case "WARNING":
			return Level.WARNING;

		case "ERROR":
		case "SEVERE":
			return Level.SEVERE;

		default:
			throw new RuntimeIOException("invalid value %", value);
		}
	}
}