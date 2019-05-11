package com.eriklievaart.toolkit.logging.api.appender;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.logging.api.Formatter;
import com.eriklievaart.toolkit.logging.api.LogConfig;

public abstract class AbstractAppender implements Appender {

	private AtomicReference<Formatter> formatterReference = new AtomicReference<>();

	@Override
	public boolean isLevelEnabled(Level test) {
		return true;
	}

	@Override
	public void setFormatter(Formatter formatter) {
		formatterReference.set(formatter);
	}

	protected Formatter getFormatter() {
		Formatter formatter = formatterReference.get();
		return formatter != null ? formatter : LogConfig.getDefaultFormatter();
	}

	protected String format(LogRecord record) {
		return getFormatter().format(record);
	}
}