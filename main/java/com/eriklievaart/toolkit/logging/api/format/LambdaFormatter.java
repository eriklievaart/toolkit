package com.eriklievaart.toolkit.logging.api.format;

import java.util.function.Function;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.logging.api.Formatter;

public class LambdaFormatter implements Formatter {

	private final Function<LogRecord, String> function;

	public LambdaFormatter(Function<LogRecord, String> function) {
		this.function = function;
	}

	@Override
	public String format(LogRecord record) {
		return function.apply(record);
	}
}