package com.eriklievaart.toolkit.logging.api.appender;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.lang.api.collection.SetTool;

public class ConsoleAppender extends AbstractAppender {

	private static final Set<Level> ERROR_LEVELS = NewCollection.concurrentSet();

	static {
		ERROR_LEVELS.addAll(SetTool.of(Level.WARNING, Level.SEVERE));
	}

	@Override
	public void append(LogRecord record) {
		String message = format(record);

		if (ERROR_LEVELS.contains(record.getLevel())) {
			System.err.println(message);
		} else {
			System.out.println(message);
		}
	}
}