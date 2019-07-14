package com.eriklievaart.toolkit.logging.api.appender;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.check.Check;

/**
 * Appender that adds LogRecords to a Collection. Intended for testing purposes.
 */
public class CollectionAppender extends AbstractAppender {

	private List<LogRecord> list = new CopyOnWriteArrayList<>();

	@Override
	protected void write(LogRecord record) {
		Check.notNull(record);
		if (isLevelEnabled(record.getLevel())) {
			list.add(record);
		}
	}

	public LogRecord peekRecord() {
		return list.get(0);
	}

	public String peekMessage() {
		return format(list.get(0));
	}

	public String popMessage() {
		return format(list.remove(0));
	}

	@Override
	public void close() {
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
}
