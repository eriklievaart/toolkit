package com.eriklievaart.toolkit.logging.api.format;

import java.util.logging.Level;

import com.eriklievaart.toolkit.lang.api.FormattedException;

public class SimpleLevelFormat {

	public static String getLevelString(Level level) {
		level.intValue();

		if (level.intValue() == Level.FINEST.intValue()) {
			return "TRACE";
		}
		if (level.intValue() == Level.FINE.intValue()) {
			return "DEBUG";
		}
		if (level.intValue() == Level.INFO.intValue()) {
			return "INFO ";
		}
		if (level.intValue() == Level.WARNING.intValue()) {
			return "WARN ";
		}
		if (level.intValue() == Level.SEVERE.intValue()) {
			return "ERROR";
		}
		throw new FormattedException("unknown level %", level);
	}
}
