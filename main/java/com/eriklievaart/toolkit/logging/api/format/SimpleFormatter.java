package com.eriklievaart.toolkit.logging.api.format;

import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.ThrowableTool;
import com.eriklievaart.toolkit.logging.api.Formatter;

public class SimpleFormatter implements Formatter {

	@Override
	public String format(LogRecord record) {
		StringBuilder builder = new StringBuilder();

		builder.append(SimpleLevelFormat.getLevelString(record.getLevel())).append(" ");
		builder.append(rightPad(getLoggerName(record)));
		builder.append(record.getMessage());

		if (record.getThrown() != null) {
			ThrowableTool.append(record.getThrown(), builder);
		}
		return builder.toString();
	}

	private static String rightPad(String arg) {
		return String.format("%-26s ", arg);
	}

	private String getLoggerName(LogRecord record) {
		if (record.getLoggerName() == null) {
			return "<null>";
		} else {
			return record.getLoggerName().replaceFirst("^com[.]eriklievaart[.].*[.]", "");
		}
	}
}