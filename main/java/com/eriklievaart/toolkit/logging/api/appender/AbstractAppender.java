package com.eriklievaart.toolkit.logging.api.appender;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.logging.api.Formatter;
import com.eriklievaart.toolkit.logging.api.LogConfig;

public abstract class AbstractAppender implements Appender {

	private AtomicReference<Formatter> formatterReference = new AtomicReference<>();
	private AtomicReference<Level> levelReference = new AtomicReference<>(Level.FINEST);

	@Override
	public boolean isLevelEnabled(Level test) {
		return levelReference.get().intValue() <= test.intValue();
	}

	@Override
	public final void append(LogRecord record) {
		Check.notNull(record);
		if (isLevelEnabled(record.getLevel())) {
			write(record);
		}
	}

	protected abstract void write(LogRecord record);

	public void setLevel(Level level) {
		Check.notNull(level);
		levelReference.set(level);
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