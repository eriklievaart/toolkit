package com.eriklievaart.toolkit.logging.api.format;

import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.ThrowableTool;
import com.eriklievaart.toolkit.logging.api.Formatter;

public class SimpleFormatter implements Formatter {

	@Override
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder();

		builder.append(SimpleLevelFormat.getLevelString(record.getLevel())).append("    ");
		builder.append(record.getLoggerName()).append("    ");
		builder.append(record.getMessage());

		if (record.getThrown() != null) {
			ThrowableTool.append(record.getThrown(), builder);
		}
		return builder.toString();
	}

}
