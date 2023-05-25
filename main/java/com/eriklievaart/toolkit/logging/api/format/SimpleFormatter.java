package com.eriklievaart.toolkit.logging.api.format;

import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.ThrowableTool;
import com.eriklievaart.toolkit.lang.api.str.StringBuilderWrapper;
import com.eriklievaart.toolkit.logging.api.Formatter;

public class SimpleFormatter implements Formatter {

	@Override
	public String format(LogRecord record) {
		StringBuilderWrapper builder = new StringBuilderWrapper();

		builder.append(SimpleLevelFormat.getLevelString(record.getLevel())).append(" ");
		builder.append(rightPad(formatLoggerName(record.getLoggerName())));
		builder.append(record.getMessage());

		if (record.getThrown() != null) {
			builder.appendLine().appendLine();
			ThrowableTool.append(record.getThrown(), builder);
		}
		return builder.toString();
	}

	static String rightPad(String arg) {
		return String.format("%-26s ", arg);
	}

	static String formatLoggerName(String name) {
		return name == null ? "<null>" : name.replaceFirst("^com[.]eriklievaart(?:[.].*)?[.]", "");
	}
}