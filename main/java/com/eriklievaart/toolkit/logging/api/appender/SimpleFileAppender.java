package com.eriklievaart.toolkit.logging.api.appender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * Simple but not so efficient Appender for writing to a file. This class is thread safe, but must have only one
 * instance per file and may cause conflicts when multiple VM's write to the same file.
 */
public class SimpleFileAppender extends AbstractAppender {
	private static final boolean APPEND = true;

	private File file;

	public SimpleFileAppender(File file) {
		this.file = file;
		file.getParentFile().mkdirs();
	}

	@Override
	public synchronized void append(LogRecord record) {
		try (FileWriter writer = new FileWriter(file, APPEND)) {
			writer.write(format(record));
			writer.write("\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
