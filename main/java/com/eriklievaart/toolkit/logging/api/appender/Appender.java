package com.eriklievaart.toolkit.logging.api.appender;

import java.io.Closeable;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.logging.api.Formatter;

public interface Appender extends Closeable {

	public void setFormatter(Formatter formatter);

	public void append(LogRecord record);

	public boolean isLevelEnabled(Level test);

	@Override
	void close();
}
