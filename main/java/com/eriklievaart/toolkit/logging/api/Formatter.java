package com.eriklievaart.toolkit.logging.api;

import java.util.logging.LogRecord;

/**
 * Formats LogRecords into a desired string. Implementations MUST be made thread safe.
 */
public interface Formatter {

	public String format(LogRecord record);

}
