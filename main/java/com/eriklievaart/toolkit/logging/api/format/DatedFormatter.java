package com.eriklievaart.toolkit.logging.api.format;

import java.util.Date;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.date.SimpleDateFormatFactory;
import com.eriklievaart.toolkit.logging.api.Formatter;

public class DatedFormatter implements Formatter {

	private Formatter delegate;
	private String format;

	public DatedFormatter() {
		this("dd-MM-yyyy HH:mm:ss");
	}

	public DatedFormatter(String dateFormat) {
		this(dateFormat, new SimpleFormatter());
	}

	public DatedFormatter(String dateFormat, Formatter delegate) {
		this.format = dateFormat;
		this.delegate = delegate;
	}

	@Override
	public String format(LogRecord record) {
		return SimpleDateFormatFactory.getWrapper(format).toString(new Date()) + " " + delegate.format(record);
	}
}